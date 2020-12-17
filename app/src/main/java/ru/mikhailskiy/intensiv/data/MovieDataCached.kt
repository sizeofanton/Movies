package ru.mikhailskiy.intensiv.data

import ru.mikhailskiy.intensiv.room.entity.MovieCache

data class MovieDataCached(
    val nowPlaying: List<MovieCache>,
    val upcoming: List<MovieCache>,
    val popular: List<MovieCache>
)
