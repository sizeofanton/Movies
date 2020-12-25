package ru.mikhailskiy.intensiv.data.dto.movie_credits

data class MovieCredits(
    val id: Int,
    val cast: List<CastMember>,
    val crew: List<CrewMember>
)
