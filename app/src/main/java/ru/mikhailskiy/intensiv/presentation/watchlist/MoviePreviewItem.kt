package ru.mikhailskiy.intensiv.presentation.watchlist

import com.squareup.picasso.Picasso
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_with_text.*
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.data.room.entity.FavoriteMovieEntity
import ru.mikhailskiy.intensiv.data.vo.FavoriteMovie

class MoviePreviewItem(
        private val content: FavoriteMovie,
        private val onClick: (movie: FavoriteMovie) -> Unit
) : Item() {

    override fun getLayout() = R.layout.item_small

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.image_preview.setOnClickListener {
            onClick.invoke(content)
        }

        Picasso.get()
            .load(content.posterPath)
            .into(viewHolder.image_preview)
    }

}