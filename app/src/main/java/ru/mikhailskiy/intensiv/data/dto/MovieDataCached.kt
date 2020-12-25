package ru.mikhailskiy.intensiv.data.dto

import ru.mikhailskiy.intensiv.data.room.entity.MovieCache

data class MovieDataCached(
    val nowPlaying: List<MovieCache>,
    val upcoming: List<MovieCache>,
    val popular: List<MovieCache>
)
