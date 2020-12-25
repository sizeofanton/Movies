package ru.mikhailskiy.intensiv.data.mapper

import ru.mikhailskiy.intensiv.data.room.entity.FavoriteMovieEntity
import ru.mikhailskiy.intensiv.data.vo.FavoriteMovie

//class FavoriteMovieMapper: BaseMapper<List<FavoriteMovieEntity>, List<FavoriteMovie>> {
//    override fun map(d: List<FavoriteMovieEntity>): List<FavoriteMovie> =
//        d.map {  dto ->
//            FavoriteMovie(
//                posterPath = dto.posterPath,
//                movieDbId = dto.movieDbId
//            )
//        }
//    }

object FavoriteMovieMapper: ViewObjectMapper<FavoriteMovie, FavoriteMovieEntity> {

    override fun toViewObject(dto: FavoriteMovieEntity): FavoriteMovie {
        return FavoriteMovie(
            posterPath = dto.posterPath,
            movieDbId = dto.movieDbId
        )
    }
}
