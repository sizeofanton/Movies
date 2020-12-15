package ru.mikhailskiy.intensiv.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.mikhailskiy.intensiv.data.PosterProvider

@Entity(tableName = "favorite")
data class FavoriteMovie(
    override val posterPath: String,
    @PrimaryKey
    val movieDbId: Int
): PosterProvider
