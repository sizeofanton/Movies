package ru.mikhailskiy.intensiv.room.dao

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import ru.mikhailskiy.intensiv.room.entity.FavoriteMovie

@Dao
interface FavoriteMovieDao {
    @Query("SELECT * FROM favorite")
    fun get(): Observable<List<FavoriteMovie>>

    @Insert
    fun insert(movie: FavoriteMovie): Completable

    @Update
    fun update(movie: FavoriteMovie): Completable

    @Delete
    fun delete(vararg movies: FavoriteMovie): Completable
}