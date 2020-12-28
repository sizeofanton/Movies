package ru.mikhailskiy.intensiv.domain.usecase.movie_details

import io.reactivex.Single
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.mikhailskiy.intensiv.data.vo.MovieDetails
import ru.mikhailskiy.intensiv.data.vo.TvShowDetails
import ru.mikhailskiy.intensiv.domain.repository.DetailsRepository

@KoinApiExtension
class MovieDetailsUseCases: KoinComponent {
    private val repository: DetailsRepository by inject()

    fun getTvShowDetails(id: Int): Single<TvShowDetails> = repository.getShowDescription(id)
    fun getMovieDetails(id: Int): Single<MovieDetails> = repository.getMovieDescription(id)


}