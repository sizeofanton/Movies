package ru.mikhailskiy.intensiv.data.room.dao

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import ru.mikhailskiy.intensiv.data.room.entity.MovieCache

@Dao
interface MovieCacheDao {
    @Query("SELECT * FROM ${MovieCache.TABLE_NAME}")
    fun get(): Observable<List<MovieCache>>

    @Insert
    fun insert(cacheEntry: MovieCache): Completable

    @Update
    fun update(cacheEntry: MovieCache): Completable

    @Delete
    fun update(vararg cacheEntries: MovieCache): Completable

    @Query("DELETE FROM ${MovieCache.TABLE_NAME}")
    fun clear(): Completable

    @Query("SELECT * FROM ${MovieCache.TABLE_NAME} WHERE type=:type")
    fun getByType(type: String): Observable<List<MovieCache>>

}