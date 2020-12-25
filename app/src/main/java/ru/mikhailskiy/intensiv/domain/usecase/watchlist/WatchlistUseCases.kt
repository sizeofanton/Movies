package ru.mikhailskiy.intensiv.domain.usecase.watchlist

import io.reactivex.Observable
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.mikhailskiy.intensiv.data.vo.FavoriteMovie
import ru.mikhailskiy.intensiv.domain.repository.FavoriteMovieRepository
import ru.mikhailskiy.intensiv.extension.useDefaultDatabaseThreads

@KoinApiExtension
class WatchlistUseCases: KoinComponent {
    private val favoriteMovieRepository: FavoriteMovieRepository by inject()

    fun getFavoriteMovies(): Observable<List<FavoriteMovie>> {
        return favoriteMovieRepository.getFavorites()
    }

}