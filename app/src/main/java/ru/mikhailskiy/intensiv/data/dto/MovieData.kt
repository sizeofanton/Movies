package ru.mikhailskiy.intensiv.data.dto

import ru.mikhailskiy.intensiv.data.dto.movie.Movie

data class MovieData(
    val upcomingMovies: List<Movie>,
    val popularMovies: List<Movie>,
    val nowPlayingMovies: List<Movie>
)
