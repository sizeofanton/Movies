package ru.mikhailskiy.intensiv.room.dao

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import ru.mikhailskiy.intensiv.room.entity.MovieCache

@Dao
interface MovieCacheDao {
    @Query("SELECT * FROM movie_cache")
    fun get(): Observable<List<MovieCache>>

    @Insert
    fun insert(cacheEntry: MovieCache): Completable

    @Update
    fun update(cacheEntry: MovieCache): Completable

    @Delete
    fun update(vararg cacheEntries: MovieCache): Completable

    @Query("DELETE FROM movie_cache")
    fun clear(): Completable

    @Query("SELECT * FROM movie_cache WHERE type=0")
    fun getNowPlaying(): Observable<List<MovieCache>>

    @Query("SELECT * FROM movie_cache WHERE type=1")
    fun getUpcoming(): Observable<List<MovieCache>>

    @Query("SELECT * FROM movie_cache WHERE type=2")
    fun getPopular(): Observable<List<MovieCache>>

}