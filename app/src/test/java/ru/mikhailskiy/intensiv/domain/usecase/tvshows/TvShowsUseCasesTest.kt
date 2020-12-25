package ru.mikhailskiy.intensiv.domain.usecase.tvshows

import io.reactivex.schedulers.Schedulers
import org.junit.Rule
import org.junit.Test
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.inject
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import ru.mikhailskiy.intensiv.di.movieAppTestModule
import org.junit.Assert.assertEquals


@KoinApiExtension
class TvShowsUseCasesTest: KoinTest {

    private val useCase: TvShowsUseCases by inject()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger()
        modules(movieAppTestModule)
    }

    @Test
    fun test() {
        useCase.getPopularTvShows()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.single())
            .subscribe({ list ->
                 assertEquals(3, list.size)
            }, {
                throw  Throwable()
            })
    }

}