package ru.mikhailskiy.intensiv.ui.feed

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function3
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.feed_fragment.*
import kotlinx.android.synthetic.main.feed_header.*
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.data.MovieData
import ru.mikhailskiy.intensiv.data.movie.Movie
import ru.mikhailskiy.intensiv.data.movie.MovieResponse
import ru.mikhailskiy.intensiv.extension.hide
import ru.mikhailskiy.intensiv.extension.show
import ru.mikhailskiy.intensiv.extension.useDefaultNetworkThreads
import ru.mikhailskiy.intensiv.network.MovieApiClient
import ru.mikhailskiy.intensiv.util.BundleProperties
import timber.log.Timber

class FeedFragment : Fragment() {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    private val subscriptions = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.feed_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.clear()
        movies_recycler_view.layoutManager = LinearLayoutManager(context)
        movies_recycler_view.adapter = adapter.apply { addAll(listOf()) }

        val searchObservable = search_toolbar.textChangedObservable

        val searchSubject: PublishSubject<String> = PublishSubject.create()
        searchObservable.subscribe(searchSubject)
        val searchSubscription = searchSubject.subscribe { query ->
            openSearch(query)
        }
        subscriptions.add(searchSubscription)


        val nowPlayingObservable = MovieApiClient.apiClient
            .getNowPlayingMovies()

        val upcomingObservable = MovieApiClient.apiClient
            .getUpcomingMovies()

        val popularObservable = MovieApiClient.apiClient
            .getPopularMovies()

        val movieDataSubscription = Single.zip(nowPlayingObservable, upcomingObservable, popularObservable,
           Function3<MovieResponse, MovieResponse, MovieResponse, MovieData>{ now, upcoming, popular ->
               MovieData(
                   nowPlayingMovies = now.results,
                   upcomingMovies = upcoming.results,
                   popularMovies = popular.results
               )
           })
            .useDefaultNetworkThreads()
            .doOnSubscribe { progress_bar.show() }
            .doOnTerminate { progress_bar.hide() }
            .subscribe({ movieData ->
                val moviesList = listOf(
                    MainCardContainer(R.string.now_playing, movieData.nowPlayingMovies.map {
                        MovieItem(it) {openMovieDetails(it)}
                    }),
                    MainCardContainer(R.string.upcoming, movieData.upcomingMovies.map {
                        MovieItem(it) {openMovieDetails(it)}
                    }),
                    MainCardContainer(R.string.popular, movieData.popularMovies.map {
                        MovieItem(it) {openMovieDetails(it)}
                    })
                )
                adapter.apply { addAll(moviesList) }
            }, { throwable ->
                Timber.e(throwable)
            })

        subscriptions.add(movieDataSubscription)

    }

    private fun openMovieDetails(movie: Movie) {
        val options = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }

        val bundle = Bundle()
        bundle.putInt(BundleProperties.ID_KEY, movie.id)
        bundle.putString(BundleProperties.TYPE_KEY, BundleProperties.TYPE_MOVIE)
        findNavController().navigate(R.id.movie_details_fragment, bundle, options)
    }

    private fun openSearch(searchText: String) {
        val options = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }

        val bundle = Bundle()
        bundle.putString(BundleProperties.SEARCH_KEY, searchText)
        findNavController().navigate(R.id.search_dest, bundle, options)
    }

    override fun onStop() {
        super.onStop()
        search_toolbar.clear()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onDestroy() {
        super.onDestroy()
        subscriptions.clear()
    }
}