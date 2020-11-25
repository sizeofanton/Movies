package ru.mikhailskiy.intensiv.ui.tvshows

import com.squareup.picasso.Picasso
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.tv_show_card.*
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.data.Movie

class TvShowItem(
    private val content: Movie,
    private val onClick: (movie: Movie) -> Unit
): Item() {

    override fun getLayout(): Int = R.layout.tv_show_card

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.description.text = content.title
        viewHolder.show_rating.rating = content.rating
        viewHolder.image_preview.setOnClickListener {
            onClick.invoke(content)
        }

        Picasso.get()
            .load("https://2x2tv.ru/upload/iblock/4af/4afce4e71d9ed521b7b17d71c0b1dd7d.jpg")
            .into(viewHolder.image_preview)
    }

}