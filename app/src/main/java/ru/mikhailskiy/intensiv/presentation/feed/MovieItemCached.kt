package ru.mikhailskiy.intensiv.presentation.feed

import com.squareup.picasso.Picasso
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_with_text.*
import kotlinx.android.synthetic.main.search_card.description
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.data.room.entity.MovieCache

class MovieItemCached(
    private val content: MovieCache,
    private val onClick: (movie: MovieCache) -> Unit
): Item() {

    override fun getLayout(): Int = R.layout.item_with_text

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.description.text = content.title
        viewHolder.movie_rating.rating = content.rating
        viewHolder.content.setOnClickListener {
            onClick.invoke(content)
        }

        Picasso.get()
            .load(R.drawable.placeholder)
            .fit()
            .into(viewHolder.image_preview)
    }

}