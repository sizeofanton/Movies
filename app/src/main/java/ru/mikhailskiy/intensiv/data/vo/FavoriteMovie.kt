package ru.mikhailskiy.intensiv.data.vo

import ru.mikhailskiy.intensiv.data.dto.PosterProvider

data class FavoriteMovie(
    override val posterPath: String,
    val movieDbId: Int
): PosterProvider