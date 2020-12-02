package ru.mikhailskiy.intensiv.ui.tvshows

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.tv_shows_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.mikhailskiy.intensiv.BuildConfig
import ru.mikhailskiy.intensiv.MovieFinderApp
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.data.tv_show.TvShow
import ru.mikhailskiy.intensiv.data.tv_show.TvShowResponse
import ru.mikhailskiy.intensiv.network.MovieApiClient
import timber.log.Timber


class TvShowsFragment : Fragment() {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tv_shows_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_shows_recycler_view.adapter = adapter.apply { addAll(listOf()) }

        MovieApiClient.apiClient
            .getPopularShow(API_KEY)
            .enqueue(object: Callback<TvShowResponse> {
                override fun onResponse(
                        call: Call<TvShowResponse>,
                        response: Response<TvShowResponse>
                ) {
                    val shows = response.body()?.results
                    shows?.let {
                        val tvShowsItems = it.map { tvShow ->
                            TvShowItem(tvShow) {
                                openTvShowDetail(tvShow)
                            }
                        }
                        tv_shows_recycler_view.adapter = adapter.apply { addAll(tvShowsItems) }
                    }
                }

                override fun onFailure(call: Call<TvShowResponse>, error: Throwable) {
                    Timber.e(error)
                }
            })
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
        bundle.putInt("id", show.id)
        bundle.putString("type", "tv_show")
        findNavController().navigate(R.id.movie_details_fragment, bundle, options)
    }

    companion object {
        private val API_KEY = BuildConfig.THE_MOVIE_DATABASE_API
    }
}