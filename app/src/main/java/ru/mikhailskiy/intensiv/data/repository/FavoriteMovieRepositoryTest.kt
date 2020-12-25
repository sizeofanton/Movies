package ru.mikhailskiy.intensiv.data.repository

import io.reactivex.Observable
import ru.mikhailskiy.intensiv.data.vo.FavoriteMovie
import ru.mikhailskiy.intensiv.domain.repository.FavoriteMovieRepository

class FavoriteMovieRepositoryTest: FavoriteMovieRepository {

    override fun getFavorites(): Observable<List<FavoriteMovie>> {
        val list = listOf<FavoriteMovie>(
            FavoriteMovie(posterPath = "", movieDbId = 1),
            FavoriteMovie(posterPath = "", movieDbId = 2),
            FavoriteMovie(posterPath = "", movieDbId = 3)
        )

        return Observable.create() { emitter ->
            emitter.onNext(list)
        }
    }
}