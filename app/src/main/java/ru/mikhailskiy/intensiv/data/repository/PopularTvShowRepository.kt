package ru.mikhailskiy.intensiv.data.repository

import io.reactivex.Single
import ru.mikhailskiy.intensiv.data.mapper.TvShowMapper
import ru.mikhailskiy.intensiv.data.network.MovieApiClient
import ru.mikhailskiy.intensiv.data.vo.TvShow
import ru.mikhailskiy.intensiv.domain.repository.TvShowsRepository

class PopularTvShowRepository(): TvShowsRepository {
    override fun getTvShows(): Single<List<TvShow>> {
        return MovieApiClient.apiClient
            .getPopularShow()
            .map { TvShowMapper.toViewObject(it.results) }
    }
}