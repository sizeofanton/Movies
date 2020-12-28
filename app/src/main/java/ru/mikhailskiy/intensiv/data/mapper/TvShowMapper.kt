package ru.mikhailskiy.intensiv.data.mapper

import ru.mikhailskiy.intensiv.data.dto.tv_show.TvShowDto
import ru.mikhailskiy.intensiv.data.vo.TvShow
object TvShowMapper: ViewObjectMapper<TvShow, TvShowDto> {
    override fun toViewObject(dto: TvShowDto): TvShow {
        return TvShow(
            id = dto.id,
            backdropUrl = dto.getBackdrop(),
            title = dto.title,
            rating = dto.rating
        )
    }
}