package ru.mikhailskiy.intensiv.domain.repository

import io.reactivex.Single
import ru.mikhailskiy.intensiv.data.vo.MovieDetails
import ru.mikhailskiy.intensiv.data.vo.TvShowDetails

interface DetailsRepository {
    fun getShowDescription(id: Int): Single<TvShowDetails>
    fun getMovieDescription(id: Int): Single<MovieDetails>
}