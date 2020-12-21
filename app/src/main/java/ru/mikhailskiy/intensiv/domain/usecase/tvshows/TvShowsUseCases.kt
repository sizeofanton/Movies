package ru.mikhailskiy.intensiv.domain.usecase.tvshows

import io.reactivex.Single
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.mikhailskiy.intensiv.data.vo.TvShow
import ru.mikhailskiy.intensiv.domain.repository.TvShowsRepository
import ru.mikhailskiy.intensiv.extension.useDefaultNetworkThreads

@KoinApiExtension
class TvShowsUseCases: KoinComponent {

    private val repository: TvShowsRepository  by inject()

    fun getPopularTvShows(): Single<List<TvShow>> =
            repository.getTvShows()
                    .useDefaultNetworkThreads()

}