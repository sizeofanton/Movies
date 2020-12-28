package ru.mikhailskiy.intensiv.data.repository

import io.reactivex.Single
import io.reactivex.functions.BiFunction
import ru.mikhailskiy.intensiv.BuildConfig
import ru.mikhailskiy.intensiv.data.dto.movie.MovieDetailsDto
import ru.mikhailskiy.intensiv.data.dto.movie_credits.Actor
import ru.mikhailskiy.intensiv.data.dto.movie_credits.MovieCreditsDto
import ru.mikhailskiy.intensiv.data.mapper.MovieDetailsMapper

import ru.mikhailskiy.intensiv.data.mapper.TvShowDetailsMapper
import ru.mikhailskiy.intensiv.data.network.MovieApiClient
import ru.mikhailskiy.intensiv.data.vo.MovieDetails
import ru.mikhailskiy.intensiv.data.vo.TvShowDetails
import ru.mikhailskiy.intensiv.domain.repository.DetailsRepository


class DetailsRepositoryNetwork: DetailsRepository {
    override fun getShowDescription(id: Int): Single<TvShowDetails> {
        return MovieApiClient.apiClient
                .getShowDescription(id)
                .map { TvShowDetailsMapper.toViewObject(it) }
    }

    override fun getMovieDescription(id: Int): Single<MovieDetails> {
        val movieDetailsObservable = MovieApiClient.apiClient
                .getMovieDetails(id)
        val movieCreditsObservable = MovieApiClient.apiClient
                .getMovieCredits(id)

        return Single.zip(
                movieDetailsObservable,
                movieCreditsObservable,
                MovieDetailsMapper.mappingFunction
        )
    }
}