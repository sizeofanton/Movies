package ru.mikhailskiy.intensiv.data.mapper

import ru.mikhailskiy.intensiv.data.dto.tv_show.TvShowDto
import ru.mikhailskiy.intensiv.data.vo.TvShow

class TvShowMapper(): BaseMapper<List<TvShowDto>, List<TvShow>> {
    override fun map(d: List<TvShowDto>): List<TvShow> =
        d.map { dto ->
            TvShow(
                id = dto.id,
                backdropUrl = dto.getBackdrop(),
                title = dto.title,
                rating = dto.rating
            )
        }
}