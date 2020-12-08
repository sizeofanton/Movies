package ru.mikhailskiy.intensiv.ui.tvshows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.tv_shows_fragment.*
import ru.mikhailskiy.intensiv.BuildConfig
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.data.tv_show.TvShow
import ru.mikhailskiy.intensiv.extension.useDefaultNetworkThreads
import ru.mikhailskiy.intensiv.network.MovieApiClient
import timber.log.Timber


class TvShowsFragment : Fragment() {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    private val subscriptions: CompositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tv_shows_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_shows_recycler_view.adapter = adapter.apply { addAll(listOf()) }

        val subscription = MovieApiClient.apiClient
            .getPopularShow(apiKey = API_KEY)
            .useDefaultNetworkThreads()
            .doOnSuccess { progress_bar.visibility = View.INVISIBLE }
            .subscribe({ response ->
                val shows = response.results
                val items = shows.map { show->
                    TvShowItem(show) {
                        openTvShowDetail(show)
                    }
                }
                tv_shows_recycler_view.adapter = adapter.apply { addAll(items) }
            }, { throwable ->
                Timber.d(throwable)
            })

        subscriptions.add(subscription)
    }

    private fun openTvShowDetail(show: TvShow) {
        val options = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }

        val bundle = Bundle()
        bundle.putInt(getString(R.string.id), show.id)
        bundle.putString(getString(R.string.type), getString(R.string.type_show))
        findNavController().navigate(R.id.movie_details_fragment, bundle, options)
    }

    companion object {
        private val API_KEY = BuildConfig.THE_MOVIE_DATABASE_API
    }

    override fun onDestroy() {
        super.onDestroy()
        subscriptions.clear()
    }
}