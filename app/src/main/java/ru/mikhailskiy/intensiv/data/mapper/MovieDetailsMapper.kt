package ru.mikhailskiy.intensiv.data.mapper

import ru.mikhailskiy.intensiv.BuildConfig
import ru.mikhailskiy.intensiv.data.dto.movie.MovieDetailsDto
import ru.mikhailskiy.intensiv.data.dto.movie_credits.Actor
import ru.mikhailskiy.intensiv.data.dto.movie_credits.MovieCreditsDto
import ru.mikhailskiy.intensiv.data.vo.MovieDetails
import ru.mikhailskiy.intensiv.util.DateParser
import ru.mikhailskiy.intensiv.util.GenreParser
import io.reactivex.functions.BiFunction

object MovieDetailsMapper {
    val mappingFunction = BiFunction<MovieDetailsDto, MovieCreditsDto, MovieDetails> { details, credits ->
        MovieDetails(
                title = details.title,
                rating = details.rating,
                backdrop = details.getBackdrop(),
                poster = details.getPoster(),
                description = details.overview,
                studio = details.productionCompanies[0].name,
                genre = GenreParser.parse(details.genres),
                year = DateParser.getYear(details.releaseDate),
                homepage = details.homepage,
                cast = mutableListOf<Actor>().apply {
                    for (i in 0 until 5)
                        add(Actor(
                                firstName = credits.cast[i].name.split(" ")[0],
                                lastName = credits.cast[i].name.split(" ")[1],
                                photoUrl = "${BuildConfig.API_IMAGE_URL}${credits.cast[i].profilePath}"
                        ))
                }
        )
    }
}