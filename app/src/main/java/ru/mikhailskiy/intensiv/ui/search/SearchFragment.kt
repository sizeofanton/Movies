package ru.mikhailskiy.intensiv.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.GridLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.feed_header.*
import kotlinx.android.synthetic.main.fragment_search.*
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.data.movie.Movie
import ru.mikhailskiy.intensiv.extension.useDefaultNetworkThreads
import ru.mikhailskiy.intensiv.network.MovieApiClient
import timber.log.Timber


class SearchFragment : Fragment() {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchTerm = requireArguments().getString("search")
        search_toolbar.setText(searchTerm)

        adapter.clear()
        movies_recycler_view.adapter = adapter.apply { addAll(listOf()) }

        searchTerm?.let {
            MovieApiClient.apiClient
                .searchMovies(searchTerm)
                .useDefaultNetworkThreads()
                .subscribe ({ response ->
                    println(response.results)
                    val movies = response.results.map {  movie ->
                        SearchItem(movie) {
                            openMovieDetails(it)
                        }
                    }
                    println(movies)
                    adapter.apply { addAll(movies) }
                }, { throwable ->
                    Timber.d(throwable)
                })
        }
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
        bundle.putInt(getString(R.string.id), movie.id)
        bundle.putString(getString(R.string.type), getString(R.string.type_movie))
        findNavController().navigate(R.id.movie_details_fragment, bundle, options)
    }
}