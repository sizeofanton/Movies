package ru.mikhailskiy.intensiv.presentation.tvshows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.tv_shows_fragment.*
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinApiExtension
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.data.dto.tv_show.TvShowDto
import ru.mikhailskiy.intensiv.extension.hide
import ru.mikhailskiy.intensiv.extension.show
import ru.mikhailskiy.intensiv.extension.useDefaultNetworkThreads
import ru.mikhailskiy.intensiv.data.network.MovieApiClient
import ru.mikhailskiy.intensiv.extension.showSnackbar
import ru.mikhailskiy.intensiv.util.BundleProperties
import timber.log.Timber


@KoinApiExtension
class TvShowsFragment : Fragment() {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    private val subscriptions: CompositeDisposable = CompositeDisposable()
    private val viewModel: TvShowsViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tv_shows_fragment, container, false)
    }

    @KoinApiExtension
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_shows_recycler_view.adapter = adapter.apply { addAll(listOf()) }

        viewModel.fetchTvShows()
        viewModel.tvShowsLiveData.observe(viewLifecycleOwner) { tvShows ->
            val items = tvShows.map {
                TvShowItem(it) { show ->
                    openTvShowDetail(show.id)
                }
            }
            tv_shows_recycler_view.adapter = adapter.apply { addAll(items) }
        }
        viewModel.errorMessageLiveData.observe(viewLifecycleOwner) { errorMsg ->
            showSnackbar(errorMsg)
        }
        viewModel.progressBarVisibility.observe(viewLifecycleOwner) { visibility ->
            progress_bar.visibility = visibility
        }

    }

    private fun openTvShowDetail(id: Int) {
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
        bundle.putString(BundleProperties.TYPE_KEY, BundleProperties.TYPE_SHOW)
        findNavController().navigate(R.id.movie_details_fragment, bundle, options)
    }

    override fun onDestroy() {
        super.onDestroy()
        subscriptions.clear()
    }
}