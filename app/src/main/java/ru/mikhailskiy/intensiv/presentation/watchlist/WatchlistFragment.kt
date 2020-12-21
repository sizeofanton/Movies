package ru.mikhailskiy.intensiv.presentation.watchlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.GridLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_watchlist.*
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinApiExtension
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.extension.showSnackbar
import ru.mikhailskiy.intensiv.util.BundleProperties
import timber.log.Timber

@KoinApiExtension
class WatchlistFragment : Fragment() {

    val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    private val viewModel: WatchlistViewModel by inject()

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
                viewModel.getFavoriteMovies()
                viewModel.favoriteMovies.observe(viewLifecycleOwner) { list ->
                    val favoriteItems = list.map { movie ->
                        MoviePreviewItem(movie) {
                            openMovieDetails(it.movieDbId)
                        }
                    }
                    movies_recycler_view.adapter = adapter.apply { addAll(favoriteItems) }
                }
                viewModel.errorMessage.observe(viewLifecycleOwner) { errorMsg ->
                    showSnackbar(errorMsg)
                }
            }

            BundleProperties.PROFILE_TO_WATCH -> {

            }
        }
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

    companion object {
        fun newInstance() = WatchlistFragment()
    }

}