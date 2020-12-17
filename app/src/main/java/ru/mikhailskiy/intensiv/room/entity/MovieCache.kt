package ru.mikhailskiy.intensiv.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters

enum class MovieType() {
    NOW_PLAYING,
    UPCOMING,
    POPULAR
}

//class MovieTypeConverter {
//    @TypeConverter
//    fun fromMovieType(status: MovieType): Int = status.ordinal
//
//    @TypeConverter
//    fun toMovieType(i: Int): MovieType {
//        return when (i) {
//            0 -> MovieType.NOW_PLAYING
//            1 -> MovieType.UPCOMING
//            2 -> MovieType.POPULAR
//            else -> MovieType.UPCOMING
//        }
//    }
//}


@Entity(tableName = "movie_cache")
//@TypeConverters(MovieTypeConverter::class)
data class MovieCache(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val movieId: Int,
    val type: String,
    val rating: Float,
    val title: String
)