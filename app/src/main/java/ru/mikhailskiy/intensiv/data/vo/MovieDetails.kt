package ru.mikhailskiy.intensiv.data.vo

import ru.mikhailskiy.intensiv.data.dto.movie_credits.Actor

data class MovieDetails(
    val title: String,
    val rating: Float,
    val backdrop: String,
    val poster: String,
    val description: String?,
    val studio: String,
    val genre: String,
    val year: String,
    val homepage: String?,
    val cast: List<Actor>
)
