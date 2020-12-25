package ru.mikhailskiy.intensiv.presentation.search

import com.squareup.picasso.Picasso
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.search_card.description
import kotlinx.android.synthetic.main.search_card.image_preview
import kotlinx.android.synthetic.main.search_card.movie_rating
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.data.dto.movie.Movie

class SearchItem(
    private val content: Movie,
    private val onClick: (movie: Movie) -> Unit
): Item() {

    override fun getLayout() = R.layout.search_card
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.description.text = content.title
        viewHolder.movie_rating.rating = content.rating
        viewHolder.image_preview.setOnClickListener {
            onClick.invoke(content)
        }

        Picasso.get()
            .load(content.getBackdrop())
            .fit()
            .into(viewHolder.image_preview)
    }



}