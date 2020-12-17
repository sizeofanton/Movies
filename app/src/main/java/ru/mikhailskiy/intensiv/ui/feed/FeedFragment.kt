package ru.mikhailskiy.intensiv.ui.feed

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.feed_fragment.*
import kotlinx.android.synthetic.main.feed_header.*
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.data.MovieData
import ru.mikhailskiy.intensiv.data.MovieDataCached
import ru.mikhailskiy.intensiv.data.movie.MovieResponse
import ru.mikhailskiy.intensiv.extension.hide
import ru.mikhailskiy.intensiv.extension.show
import ru.mikhailskiy.intensiv.extension.useDefaultDatabaseThreads
import ru.mikhailskiy.intensiv.extension.useDefaultNetworkThreads
import ru.mikhailskiy.intensiv.network.MovieApiClient
import ru.mikhailskiy.intensiv.room.AppDatabase
import ru.mikhailskiy.intensiv.room.entity.MovieType
import ru.mikhailskiy.intensiv.room.entity.MovieCache
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

        progress_bar.show()
//        val connected =
//                if (activity is ConnectionAware)
//                        (activity as ConnectionAware).isConnectedToNetwork()
//                else false

        //if (connected) fetchFromBackend() else fetchFromLocalDb()
        fetchFromBackend()
    }

    private fun openMovieDetails(id: Int) {
        val options = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }

        val bundle = Bundle()
        bundle.putInt(BundleProperties.ID_KEY, id)
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

    private fun cacheMovies(movieData: MovieData) {
        val localDb = AppDatabase.newInstance(requireContext())
        val cachingSubscription = localDb.cache()
            .clear()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe {
                for (movie in movieData.nowPlayingMovies) {
                    localDb.cache()
                        .insert(
                            MovieCache(
                                id = null,
                                movieId = movie.id,
                                type = MovieType.NOW_PLAYING.name,
                                rating = movie.rating,
                                title = movie.title
                            )
                        )
                        .subscribe()
                }

                for (movie in movieData.upcomingMovies) {
                    localDb.cache()
                        .insert(
                            MovieCache(
                                id = null,
                                movieId = movie.id,
                                type = MovieType.UPCOMING.name,
                                rating = movie.rating,
                                title = movie.title
                            )
                        )
                        .subscribe()
                }

                for (movie in movieData.popularMovies) {
                    localDb.cache()
                        .insert(
                            MovieCache(
                                id = null,
                                movieId = movie.id,
                                type = MovieType.POPULAR.name,
                                rating = movie.rating,
                                title =  movie.title
                            )
                        )
                        .subscribe()
                }
            }
        subscriptions.add(cachingSubscription)

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

    private fun fetchFromBackend() {
        val nowPlayingObservable = MovieApiClient.apiClient
            .getNowPlayingMovies()

        val upcomingObservable = MovieApiClient.apiClient
            .getUpcomingMovies()

        val popularObservable = MovieApiClient.apiClient
            .getPopularMovies()

        val movieDataSubscription =
            Single.zip(nowPlayingObservable, upcomingObservable, popularObservable,
                Function3<MovieResponse, MovieResponse, MovieResponse, MovieData> { now, upcoming, popular ->
                    MovieData(
                        nowPlayingMovies = now.results,
                        upcomingMovies = upcoming.results,
                        popularMovies = popular.results
                    )
                })
                .useDefaultNetworkThreads()
                .doOnTerminate { progress_bar.hide() }
                .subscribe({ movieData ->
                    val moviesList = listOf(
                        MainCardContainer(R.string.now_playing, movieData.nowPlayingMovies.map {
                            MovieItem(it) { openMovieDetails(it.id) }
                        }),
                        MainCardContainer(R.string.upcoming, movieData.upcomingMovies.map {
                            MovieItem(it) { openMovieDetails(it.id) }
                        }),
                        MainCardContainer(R.string.popular, movieData.popularMovies.map {
                            MovieItem(it) { openMovieDetails(it.id) }
                        })
                    )
                    adapter.apply { addAll(moviesList) }
                    cacheMovies(movieData)

                }, { throwable ->
                    //Timber.e(throwable)
                    fetchFromLocalDb()
                })

        subscriptions.add(movieDataSubscription)
    }

    private fun fetchFromLocalDb() {
        val localDb = AppDatabase.newInstance(requireContext())
        val nowPlayingObservable = localDb.cache()
            .getByType(MovieType.NOW_PLAYING.name)
        val upcomingObservable = localDb.cache()
            .getByType(MovieType.UPCOMING.name)
        val popularObservable = localDb.cache()
            .getByType(MovieType.POPULAR.name)

        val movieDataSubscription = Observable.zip(
            nowPlayingObservable,
            upcomingObservable,
            popularObservable,
            Function3<List<MovieCache>, List<MovieCache>, List<MovieCache>, MovieDataCached>() { now, upcoming, popular ->
                MovieDataCached(
                    nowPlaying = now,
                    upcoming = upcoming,
                    popular = popular
                )
            }
        )
            .doOnSubscribe { progress_bar.hide() }
            .doOnEach { progress_bar.hide() }
            .useDefaultDatabaseThreads()
            .subscribe({ movieData ->
                val moviesList = listOf(
                    MainCardContainer(R.string.now_playing, movieData.nowPlaying.map {
                        MovieItemCached(it) { openMovieDetails(it.movieId) }
                    }),
                    MainCardContainer(R.string.upcoming, movieData.upcoming.map {
                        MovieItemCached(it) { openMovieDetails(it.movieId) }
                    }),
                    MainCardContainer(R.string.popular, movieData.popular.map {
                        MovieItemCached(it) { openMovieDetails(it.movieId) }
                    })
                )
                adapter.apply { addAll(moviesList) }


            }, { throwable ->
                Timber.e(throwable)
            })

        subscriptions.add(movieDataSubscription)
    }
}