package ru.mikhailskiy.intensiv.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.mikhailskiy.intensiv.data.room.dao.FavoriteMovieDao
import ru.mikhailskiy.intensiv.data.room.dao.MovieCacheDao
import ru.mikhailskiy.intensiv.data.room.entity.FavoriteMovieEntity
import ru.mikhailskiy.intensiv.data.room.entity.MovieCache

private const val DB_NAME = "Movie.db"

@Database(entities = [FavoriteMovieEntity::class, MovieCache::class], version = 3)
abstract class AppDatabase: RoomDatabase() {

    abstract fun favorites(): FavoriteMovieDao
    abstract fun cache(): MovieCacheDao

    companion object {
        fun newInstance(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}