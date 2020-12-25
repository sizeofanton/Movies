package ru.mikhailskiy.intensiv.domain.usecase.watchlist

import io.reactivex.schedulers.Schedulers
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertEquals
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.inject
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import ru.mikhailskiy.intensiv.di.movieAppTestModule
import ru.mikhailskiy.intensiv.domain.usecase.watchlist.WatchlistUseCases


@KoinApiExtension
class WatchlistUseCasesTest: KoinTest {

    private val useCase: WatchlistUseCases by inject()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger()
        modules(movieAppTestModule)
    }

    @Test
    fun test() {
        useCase.getFavoriteMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.single())
            .subscribe { list ->
                assertEquals(3, list.size)
            }
    }

}