package ru.mikhailskiy.intensiv.ui.watchlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.GridLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_watchlist.*
import kotlinx.android.synthetic.main.movie_details_fragment.*
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.data.MockRepository
import ru.mikhailskiy.intensiv.data.movie.Movie
import ru.mikhailskiy.intensiv.extension.useDefaultDatabaseThreads
import ru.mikhailskiy.intensiv.room.AppDatabase
import ru.mikhailskiy.intensiv.room.entity.FavoriteMovie
import ru.mikhailskiy.intensiv.util.BundleProperties
import timber.log.Timber

class WatchlistFragment : Fragment() {

    val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_watchlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.clear()
        movies_recycler_view.layoutManager = GridLayoutManager(context, 4)
        movies_recycler_view.adapter = adapter.apply { addAll(listOf()) }
        when (arguments?.getString(BundleProperties.PROFILE_TAB_ID)) {
            BundleProperties.PROFILE_LIKED -> {
                val subcription = AppDatabase.newInstance(requireContext())
                    .favorites()
                    .get()
                    .useDefaultDatabaseThreads()
                    .subscribe({ list ->
                        Timber.e(list.toString())
                        val favoriteList = list.map { movie ->
                            MoviePreviewItem(movie) { movie ->
                                openMovieDetails(movie)
                            }
                        }
                        movies_recycler_view.adapter = adapter.apply { addAll(favoriteList) }
                    },{ throwable ->
                        Timber.e(throwable)
                    })

            }

            BundleProperties.PROFILE_TO_WATCH -> {

            }
        }

        //movies_recycler_view.adapter = adapter.apply { addAll(moviesList) }
    }

    private fun openMovieDetails(movie: FavoriteMovie) {
        val options = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }

        val bundle = Bundle()
        bundle.putInt(BundleProperties.ID_KEY, movie.movieDbId)
        bundle.putString(BundleProperties.TYPE_KEY, BundleProperties.TYPE_MOVIE)
        findNavController().navigate(R.id.movie_details_fragment, bundle, options)
    }

    companion object {
        fun newInstance() = WatchlistFragment()
    }

}