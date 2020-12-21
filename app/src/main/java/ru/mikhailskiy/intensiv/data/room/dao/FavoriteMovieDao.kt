package ru.mikhailskiy.intensiv.data.room.dao

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import ru.mikhailskiy.intensiv.data.room.entity.FavoriteMovieEntity

@Dao
interface FavoriteMovieDao {
    @Query("SELECT * FROM favorite")
    fun get(): Observable<List<FavoriteMovieEntity>>

    @Insert
    fun insert(movie: FavoriteMovieEntity): Completable

    @Update
    fun update(movie: FavoriteMovieEntity): Completable

    @Delete
    fun delete(vararg movies: FavoriteMovieEntity): Completable
}