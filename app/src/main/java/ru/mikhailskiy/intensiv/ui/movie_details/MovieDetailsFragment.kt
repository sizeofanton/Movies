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
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.movie_details_fragment.*
import ru.mikhailskiy.intensiv.BuildConfig
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.data.movie.MovieDetails
import ru.mikhailskiy.intensiv.data.movie_credits.Actor
import ru.mikhailskiy.intensiv.data.movie_credits.CastMember
import ru.mikhailskiy.intensiv.data.tv_show.TvShowDetails
import ru.mikhailskiy.intensiv.extension.useDefaultNetworkThreads
import ru.mikhailskiy.intensiv.network.MovieApiClient
import ru.mikhailskiy.intensiv.util.DateParser
import ru.mikhailskiy.intensiv.util.GenreParser
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
        description.movementMethod = ScrollingMovementMethod()
        actors.adapter = adapter.apply { addAll(listOf()) }
        button_back.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        when (arguments?.getString(getString(R.string.type))) {
            getString(R.string.type_show) -> {
                val id = requireArguments().getInt(getString(R.string.id))
                val tvShowDetailsSubscription = MovieApiClient.apiClient
                    .getShowDescription(id, API_KEY)
                    .useDefaultNetworkThreads()
                    .subscribe({ details ->
                        setupTvShow(details)
                    }, { throwable->
                        Timber.d(throwable)
                    })
                subscriptions.add(tvShowDetailsSubscription)
            }

            getString(R.string.type_movie) -> {
                val id = requireArguments().getInt(getString(R.string.id))

                val movieDetailSubscription = MovieApiClient.apiClient
                    .getMovieDetails(id, API_KEY)
                    .useDefaultNetworkThreads()
                    .subscribe({ details ->
                        setupMovie(details, id)
                    }, { throwable ->
                        Timber.d(throwable)
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
            .getMovieCredits(id, API_KEY)
            .useDefaultNetworkThreads()
            .subscribe({ response ->
                val topCast = response.cast.sortedByDescending { it.popularity }
                setupCast(topCast)
            },{ throwable ->
                Timber.d(throwable)
            })

        subscriptions.add(creditSubscription)

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
                        getString(R.string.placeholder_url)
                    }
                )
            )
        }
        val actorsItems = actors.map { ActorItem(it) }
        adapter.apply { addAll(actorsItems) }
    }

    override fun onDestroy() {
        super.onDestroy()
        //subscriptions.clear()
    }

    companion object {
        private const val API_KEY = BuildConfig.THE_MOVIE_DATABASE_API
    }
}