package ru.mikhailskiy.intensiv.ui.movie_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.movie_details_fragment.*
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.data.MockRepository
import ru.mikhailskiy.intensiv.data.MovieDetailed

class MovieDetailsFragment : Fragment() {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    private var favoriteState = false
        set(bool) {
            val icon = if (bool) {
                resources.getDrawable(R.drawable.ic_favorite_red, activity?.theme)
            } else {
                resources.getDrawable(R.drawable.ic_favorite, activity?.theme)
            }
            favorite.setImageDrawable(icon)
            field = bool
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movie_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actors.adapter = adapter.apply { addAll(listOf()) }
        MockRepository.getMovieDetailed().also {
            setupUI(it)
        }

    }

    private fun setupUI(details: MovieDetailed) {
        title.text = details.title
        movie_rating.rating = details.rating
        Picasso.get()
                .load(details.posterUrl)
                .fit()
                .into(movie_poster)
        description.text = details.description
        studio.text = details.studioName
        genre.text = details.genre
        year.text = year.text
        val actorsList = details.actorsList?.map { ActorItem(it) }
        adapter.apply { actorsList?.let { addAll(it) } }

        button_back.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        button_watch.setOnClickListener {
            // TODO Перенаправление, но куда?
        }

        favorite.setOnClickListener {
            favoriteState = !favoriteState
        }
    }
}