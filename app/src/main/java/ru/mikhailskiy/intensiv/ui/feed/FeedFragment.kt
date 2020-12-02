package ru.mikhailskiy.intensiv.ui.feed

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.feed_fragment.*
import kotlinx.android.synthetic.main.feed_header.*
import kotlinx.android.synthetic.main.search_toolbar.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.mikhailskiy.intensiv.BuildConfig
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.data.movie.Movie
import ru.mikhailskiy.intensiv.data.movie.MovieResponse
import ru.mikhailskiy.intensiv.network.MovieApiClient
import ru.mikhailskiy.intensiv.ui.afterTextChanged
import timber.log.Timber

class FeedFragment : Fragment() {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

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

        search_toolbar.search_edit_text.afterTextChanged {
            Timber.d(it.toString())
            if (it.toString().length > 3) {
                openSearch(it.toString())
            }
        }

        MovieApiClient.apiClient
            .getNowPlayingMovies(API_KEY)
            .enqueue(object: Callback<MovieResponse>{
                override fun onResponse(
                        call: Call<MovieResponse>,
                        response: Response<MovieResponse>
                ) {
                    val movies = response.body()?.results
                    movies?.let {
                        val moviesList = listOf(
                            MainCardContainer(
                                R.string.now_playing,
                                it.map { movie ->
                                    MovieItem(movie) {
                                        openMovieDetails(it)
                                    }
                                }
                            )
                        )
                        adapter.apply { addAll(moviesList) }
                    }
                }

                override fun onFailure(call: Call<MovieResponse>, error: Throwable) {
                    Timber.d(error)
                }
            })


        MovieApiClient.apiClient
            .getUpcomingMovies(API_KEY)
            .enqueue(object: Callback<MovieResponse>{
                override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                    val movies = response.body()?.results
                    movies?.let {
                        val moviesList = listOf(
                                MainCardContainer(
                                        R.string.upcoming,
                                        it.map { movie ->
                                            MovieItem(movie) {
                                                openMovieDetails(it)
                                            }
                                        }
                                )
                        )
                        adapter.apply { addAll(moviesList) }
                    }
                }

                override fun onFailure(call: Call<MovieResponse>, error: Throwable) {
                    Timber.d(error)
                }
            })

        MovieApiClient.apiClient
                .getPopularMovies(API_KEY)
                .enqueue(object: Callback<MovieResponse>{
                    override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                        val movies = response.body()?.results
                        movies?.let {
                            val moviesList = listOf(
                                    MainCardContainer(
                                            R.string.popular,
                                            it.map { movie ->
                                                MovieItem(movie) {
                                                    openMovieDetails(it)
                                                }
                                            }
                                    )
                            )
                            adapter.apply { addAll(moviesList) }
                        }
                    }

                    override fun onFailure(call: Call<MovieResponse>, error: Throwable) {
                        Timber.d(error)
                    }
                })
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
        bundle.putInt("id", movie.id)
        bundle.putString("type", "movie")
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
        bundle.putString("search", searchText)
        findNavController().navigate(R.id.search_dest, bundle, options)
    }

    override fun onStop() {
        super.onStop()
        search_toolbar.clear()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    companion object {
        const val API_KEY = BuildConfig.THE_MOVIE_DATABASE_API
    }
}