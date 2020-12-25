package ru.mikhailskiy.intensiv.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class MovieType() {
    NOW_PLAYING,
    UPCOMING,
    POPULAR
}

@Entity(tableName = MovieCache.TABLE_NAME)
data class MovieCache(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val movieId: Int,
    val type: String,
    val rating: Float,
    val title: String
) {
    companion object {
        const val TABLE_NAME = "movie_cache"
    }
}