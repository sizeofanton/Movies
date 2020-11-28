package ru.mikhailskiy.intensiv.ui.movie_details

import com.squareup.picasso.Picasso
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.actor_card.*
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.data.Actor

class ActorItem(
    private val content: Actor
): Item() {

    override fun getLayout(): Int = R.layout.actor_card

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.actor_firstname.text = content.firstName
        viewHolder.actor_lastname.text = content.lastName

        Picasso.get()
            .load(content.photoUrl)
            .resize(70, 60)
            .transform(CropCircleTransformation())
            .into(viewHolder.actor_photo)
    }

}