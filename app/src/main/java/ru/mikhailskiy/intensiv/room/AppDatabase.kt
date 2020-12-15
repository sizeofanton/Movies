package ru.mikhailskiy.intensiv.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.mikhailskiy.intensiv.room.dao.FavoriteMovieDao
import ru.mikhailskiy.intensiv.room.dao.MovieCacheDao
import ru.mikhailskiy.intensiv.room.entity.FavoriteMovie
import ru.mikhailskiy.intensiv.room.entity.MovieCache

private const val DB_NAME = "Movie.db"

@Database(entities = [FavoriteMovie::class, MovieCache::class], version = 2)
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