package ru.mikhailskiy.intensiv.domain.repository

import io.reactivex.Observable
import ru.mikhailskiy.intensiv.data.vo.FavoriteMovie


interface FavoriteMovieRepository {
    fun getFavorites(): Observable<List<FavoriteMovie>>
}