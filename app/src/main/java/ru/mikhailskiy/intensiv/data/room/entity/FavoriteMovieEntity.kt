package ru.mikhailskiy.intensiv.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.mikhailskiy.intensiv.data.dto.PosterProvider

@Entity(tableName = FavoriteMovieEntity.TABLE_NAME)
data class FavoriteMovieEntity(
    val posterPath: String,
    @PrimaryKey
    val movieDbId: Int
) {
    companion object {
        const val TABLE_NAME = "favorite"
    }
}
