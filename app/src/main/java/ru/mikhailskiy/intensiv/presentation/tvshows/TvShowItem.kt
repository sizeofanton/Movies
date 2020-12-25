package ru.mikhailskiy.intensiv.presentation.tvshows

import com.squareup.picasso.Picasso
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.tv_show_card.*
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.data.dto.tv_show.TvShowDto
import ru.mikhailskiy.intensiv.data.vo.TvShow

class TvShowItem(
    private val content: TvShow,
    private val onClick: (tvShow: TvShow) -> Unit
): Item() {

    override fun getLayout(): Int = R.layout.tv_show_card

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.description.text = content.title
        viewHolder.show_rating.rating = content.rating
        viewHolder.image_preview.setOnClickListener {
            onClick.invoke(content)
        }

        Picasso.get()
            .load(content.backdropUrl)
            .fit()
            .into(viewHolder.image_preview)
    }

}