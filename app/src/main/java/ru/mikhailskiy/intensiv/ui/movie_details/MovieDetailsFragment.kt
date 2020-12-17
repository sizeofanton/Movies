package ru.mikhailskiy.intensiv.ui.movie_details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.movie_details_fragment.*
import ru.mikhailskiy.intensiv.BuildConfig
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.data.movie.MovieDetails
import ru.mikhailskiy.intensiv.data.movie_credits.Actor
import ru.mikhailskiy.intensiv.data.movie_credits.CastMember
import ru.mikhailskiy.intensiv.data.tv_show.TvShowDetails
import ru.mikhailskiy.intensiv.extension.getObservable
import ru.mikhailskiy.intensiv.extension.hide
import ru.mikhailskiy.intensiv.extension.show
import ru.mikhailskiy.intensiv.extension.useDefaultNetworkThreads
import ru.mikhailskiy.intensiv.network.MovieApiClient
import ru.mikhailskiy.intensiv.room.AppDatabase
import ru.mikhailskiy.intensiv.room.entity.FavoriteMovie
import ru.mikhailskiy.intensiv.util.DateParser
import ru.mikhailskiy.intensiv.util.GenreParser
import ru.mikhailskiy.intensiv.util.BundleProperties
import timber.log.Timber

class MovieDetailsFragment : Fragment() {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    private val subscriptions = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movie_details_fragment, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewsList = listOf<View>(
            movie_poster,
            year,
            genre,
            title,
            button_watch,
            movie_rating,
            iv_4k,
            description,
            button_back,
            tv_year,
            tv_genre,
            tv_studio,
            actors,
            studio,
            favorite
        ).apply { hide() }
        description.movementMethod = ScrollingMovementMethod()
        actors.adapter = adapter.apply { addAll(listOf()) }
        button_back.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        when (arguments?.getString(BundleProperties.TYPE_KEY)) {
            BundleProperties.TYPE_SHOW -> {
                val id = requireArguments().getInt(BundleProperties.ID_KEY)
                val tvShowDetailsSubscription = MovieApiClient.apiClient
                    .getShowDescription(id)
                    .useDefaultNetworkThreads()
                    .doOnSubscribe {
                        progress_bar.show()
                        viewsList.hide()
                    }
                    .doOnTerminate {
                        progress_bar.hide()
                        viewsList.show()
                    }
                    .subscribe({ details ->
                        setupTvShow(details)
                    }, { throwable->
                        Timber.e(throwable)
                    })
                subscriptions.add(tvShowDetailsSubscription)
            }

            BundleProperties.TYPE_MOVIE -> {
                val id = requireArguments().getInt(BundleProperties.ID_KEY)

                val movieDetailSubscription = MovieApiClient.apiClient
                    .getMovieDetails(id)
                    .useDefaultNetworkThreads()
                    .doOnSubscribe {
                        progress_bar.show()
                        viewsList.hide()
                    }
                    .doOnTerminate {
                        progress_bar.hide()
                        viewsList.show()
                    }
                    .subscribe({ details ->
                        setupMovie(details, id)
                    }, { throwable ->
                        Timber.e(throwable)
                    })
                subscriptions.add(movieDetailSubscription)
            }

        }


    }

    private fun setupTvShow(details: TvShowDetails) {
        title.text = details.title
        movie_rating.rating = details.rating
        Picasso.get()
            .load(details.getBackdrop())
            .fit()
            .into(movie_poster)
        iv_4k.visibility = View.INVISIBLE
        description.text = details.overview
        studio.text = details.networks[0].name
        genre.text = GenreParser.parse(details.genres)
        year.text = DateParser.getYearInterval(
            details.firstAirDate,
            details.lastAirDate,
            DateParser.TheMovieDbFormat
        )
        button_watch.setOnClickListener {
            Intent(Intent.ACTION_VIEW, Uri.parse(details.homepage)).also {
                startActivity(it)
            }
        }
        adapter.clear()
        favorite.hide()
    }

    private fun setupMovie(details: MovieDetails, id: Int) {
        title.text = details.title
        movie_rating.rating = details.rating
        Picasso.get()
            .load(details.getBackdrop())
            .fit()
            .into(movie_poster)
        if (details.video) iv_4k.visibility = View.VISIBLE
        description.text = details.overview
        studio.text = if (details.productionCompanies.isNotEmpty()){
            details.productionCompanies[0].name
        } else "-"
        genre.text = GenreParser.parse(details.genres)
        year.text = DateParser.getYear(details.releaseDate, DateParser.TheMovieDbFormat)
        button_watch.setOnClickListener {
            Intent(Intent.ACTION_VIEW, Uri.parse(details.homepage)).also {
                startActivity(it)
            }
        }
        adapter.clear()

        val creditSubscription = MovieApiClient.apiClient
            .getMovieCredits(id)
            .useDefaultNetworkThreads()
            .subscribe({ response ->
                val topCast = response.cast.sortedByDescending { it.popularity }
                setupCast(topCast)
            },{ throwable ->
                Timber.e(throwable)
            })

        subscriptions.add(creditSubscription)

        val db = AppDatabase.newInstance(requireContext())
        val currentMovie = FavoriteMovie(details.getPoster(), details.id)
        val favoritesSubscription = db.favorites().get()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe{ list ->
                favorite.isChecked = list.contains(currentMovie)
            }
//        favorite.setOnCheckedChangeListener { _, b ->
//            if (b) {
//                db.favorites()
//                    .insert(currentMovie)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(Schedulers.io())
//                    .subscribe({}, { throwable ->
//                    Timber.e(throwable)
//                })
//            } else {
//                db.favorites()
//                    .delete(currentMovie)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(Schedulers.io())
//                    .subscribe({}, { throwable ->
//                    Timber.e(throwable)
//                })
//            }
//        }
        subscriptions.add(favoritesSubscription)

        val checkBoxSubscription = favorite.getObservable()
            .subscribe({ checked ->
               if (checked) {
                   db.favorites()
                       .insert(currentMovie)
                       .subscribeOn(Schedulers.io())
                       .subscribe({}, { throwable ->
                           Timber.e(throwable)
                       })
               } else {
                   db.favorites()
                       .delete(currentMovie)
                       .subscribeOn(Schedulers.io())
                       .subscribe({}, { throwable ->
                           Timber.e(throwable)
                       })
               }
            },{ throwable ->
                Timber.e(throwable)
            })
        subscriptions.add(checkBoxSubscription)

    }

    private fun setupCast(cast: List<CastMember>) {
        val actors = mutableListOf<Actor>()
        for (actor in cast) {
            actors.add(
                Actor(
                    firstName = actor.name.split(" ")[0],
                    lastName = actor.name.split(" ")[1],
                    photoUrl = if (actor.profilePath != null) {
                        "${BuildConfig.API_IMAGE_URL}${actor.profilePath}"
                    } else {
                        Actor.placeholderUrl
                    }
                )
            )
        }
        val actorsItems = actors.map { ActorItem(it) }
        adapter.apply { addAll(actorsItems) }
    }

    override fun onDestroy() {
        super.onDestroy()
        subscriptions.clear()
    }
}