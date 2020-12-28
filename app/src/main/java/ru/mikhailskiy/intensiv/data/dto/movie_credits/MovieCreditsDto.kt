package ru.mikhailskiy.intensiv.data.dto.movie_credits

data class MovieCreditsDto(
        val id: Int,
        val cast: List<CastMemberDto>,
        val crew: List<CrewMemberDto>
)
