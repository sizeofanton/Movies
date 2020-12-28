package ru.mikhailskiy.intensiv.presentation.movie_details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.movie_details_fragment.*
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinApiExtension
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.data.dto.movie_credits.Actor
import ru.mikhailskiy.intensiv.data.vo.MovieDetails
import ru.mikhailskiy.intensiv.data.vo.TvShowDetails
import ru.mikhailskiy.intensiv.extension.getObservable
import ru.mikhailskiy.intensiv.extension.hide
import ru.mikhailskiy.intensiv.extension.show
import ru.mikhailskiy.intensiv.extension.showSnackbar
import ru.mikhailskiy.intensiv.util.BundleProperties
import ru.mikhailskiy.intensiv.util.DataLoadingStates
import timber.log.Timber

@KoinApiExtension
class MovieDetailsFragment : Fragment() {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    private val viewModel: MovieDetailsViewModel by inject()
    private val subscriptions: CompositeDisposable by inject()

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
                viewModel.getTvShowDetails(id)
                viewModel.tvShowDetails.observe(viewLifecycleOwner) { details ->
                    setupTvShow(details)
                }
                viewModel.loadingState.observe(viewLifecycleOwner) {
                    when (it) {
                        DataLoadingStates.Loaded -> viewsList.show()
                        DataLoadingStates.Loading -> viewsList.hide()
                        DataLoadingStates.Error -> {
                            viewsList.hide()
                            showSnackbar("Error while loading details")
                        }
                    }
                }


            }

            BundleProperties.TYPE_MOVIE -> {
                val id = requireArguments().getInt(BundleProperties.ID_KEY)
//                val movieDetailSubscription = MovieApiClient.apiClient
//                    .getMovieDetails(id)
//                    .useDefaultNetworkThreads()
//                    .doOnSubscribe {
//                        progress_bar.show()
//                        viewsList.hide()
//                    }
//                    .doOnTerminate {
//                        progress_bar.hide()
//                        viewsList.show()
//                    }
//                    .subscribe({ details ->
//                        setupMovie(details, id)
//                    }, { throwable ->
//                        Timber.e(throwable)
//                    })
//                subscriptions.add(movieDetailSubscription)
                viewModel.getMovieDetails(id)
                viewModel.movieDetails.observe(viewLifecycleOwner) { details ->
                    setupMovie(details, id)
                }
                viewModel.loadingState.observe(viewLifecycleOwner) {
                    when (it) {
                        DataLoadingStates.Loaded -> viewsList.show()
                        DataLoadingStates.Loading -> viewsList.hide()
                        DataLoadingStates.Error -> {
                            viewsList.hide()
                            showSnackbar(getString(R.string.details_loading_error))
                        }
                    }
                }
            }

        }


    }

    private fun setupTvShow(details: TvShowDetails) {
        title.text = details.title
        movie_rating.rating = details.rating
        Picasso.get()
            .load(details.backdrop)
            .fit()
            .into(movie_poster)
        iv_4k.visibility = View.INVISIBLE
        description.text = details.description
        studio.text = details.studio
        genre.text = details.genre
        year.text = details.year
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
            .load(details.backdrop)
            .fit()
            .into(movie_poster)
        iv_4k.visibility = View.VISIBLE
        description.text = details.description
        studio.text = if (details.studio.isNotEmpty()) details.studio else "-"
        genre.text = details.genre
        year.text = details.year
        button_watch.setOnClickListener {
            Intent(Intent.ACTION_VIEW, Uri.parse(details.homepage)).also {
                startActivity(it)
            }
        }
        adapter.clear()
        setupCast(details.cast)

        viewModel.checkIfMovieIsFavorite(id)
        viewModel.movieFavorite.observe(viewLifecycleOwner) { isFavorite ->
            favorite.isChecked = isFavorite
        }


        val checkBoxSubscription = favorite.getObservable()
            .subscribe({ checked ->
               if (checked) {
                   viewModel.addToFavorites(id, details.poster)
               } else {
                   viewModel.removeFromFavorites(id, details.poster)
               }
            },{ throwable ->
                Timber.e(throwable)
            })
        subscriptions.add(checkBoxSubscription)
    }

    private fun setupCast(cast: List<Actor>) {
        val actorsItems = cast.map { ActorItem(it) }
        adapter.apply { addAll(actorsItems) }
    }

    override fun onDestroy() {
        super.onDestroy()
        subscriptions.clear()
    }
}