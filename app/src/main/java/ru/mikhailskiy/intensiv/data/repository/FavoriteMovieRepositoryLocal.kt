package ru.mikhailskiy.intensiv.data.repository

import io.reactivex.Observable
import ru.mikhailskiy.intensiv.MovieFinderApp
import ru.mikhailskiy.intensiv.data.mapper.FavoriteMovieMapper
import ru.mikhailskiy.intensiv.data.room.entity.FavoriteMovieEntity
import ru.mikhailskiy.intensiv.data.vo.FavoriteMovie
import ru.mikhailskiy.intensiv.domain.repository.FavoriteMovieRepository
import ru.mikhailskiy.intensiv.extension.useDefaultDatabaseThreads

class FavoriteMovieRepositoryLocal: FavoriteMovieRepository {
    override fun getFavorites(): Observable<List<FavoriteMovie>> =
        MovieFinderApp.appDatabase
                .favorites()
                .get()
                .map { list -> FavoriteMovieMapper.toViewObject(list) }
}