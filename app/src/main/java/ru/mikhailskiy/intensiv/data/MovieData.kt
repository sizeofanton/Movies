package ru.mikhailskiy.intensiv.data

import ru.mikhailskiy.intensiv.data.movie.Movie

data class MovieData(
    val upcomingMovies: List<Movie>,
    val popularMovies: List<Movie>,
    val nowPlayingMovies: List<Movie>
)
