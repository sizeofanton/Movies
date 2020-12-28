package ru.mikhailskiy.intensiv.data.mapper

import ru.mikhailskiy.intensiv.data.dto.tv_show.TvShowDetailsDto
import ru.mikhailskiy.intensiv.data.vo.TvShowDetails
import ru.mikhailskiy.intensiv.util.DateParser
import ru.mikhailskiy.intensiv.util.GenreParser

object TvShowDetailsMapper: ViewObjectMapper<TvShowDetails, TvShowDetailsDto> {
    override fun toViewObject(dto: TvShowDetailsDto): TvShowDetails {
        return TvShowDetails(
            title = dto.title,
            rating = dto.rating,
            backdrop = dto.getBackdrop(),
            description = dto.overview,
            studio = dto.productionCompanies[0].name,
            genre = GenreParser.parse(dto.genres),
            year = DateParser.getYearInterval(
                    dto.firstAirDate,
                    dto.lastAirDate,
                    DateParser.TheMovieDbFormat
            ),
            homepage = dto.homepage
        )
    }
}