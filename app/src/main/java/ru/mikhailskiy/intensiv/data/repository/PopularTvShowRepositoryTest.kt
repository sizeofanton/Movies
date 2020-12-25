package ru.mikhailskiy.intensiv.data.repository

import io.reactivex.Single
import ru.mikhailskiy.intensiv.data.vo.TvShow
import ru.mikhailskiy.intensiv.domain.repository.TvShowsRepository

class PopularTvShowRepositoryTest: TvShowsRepository  {

    override fun getTvShows(): Single<List<TvShow>> {
        val list = listOf<TvShow>(
            TvShow(id = 0, backdropUrl = "", title = "TvShow1", rating = 2.0f),
            TvShow(id = 1, backdropUrl = "", title = "TvShow2", rating = 5.0f),
            TvShow(id = 2, backdropUrl = "", title = "TvShow3", rating = 9.0f)
        )

        return Single.create { emitter ->
            emitter.onSuccess(list)
        }
    }
}