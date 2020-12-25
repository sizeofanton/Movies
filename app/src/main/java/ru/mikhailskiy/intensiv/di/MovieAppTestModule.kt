package ru.mikhailskiy.intensiv.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module
import ru.mikhailskiy.intensiv.data.repository.FavoriteMovieRepositoryLocal
import ru.mikhailskiy.intensiv.data.repository.FavoriteMovieRepositoryTest
import ru.mikhailskiy.intensiv.data.repository.PopularTvShowRepository
import ru.mikhailskiy.intensiv.data.repository.PopularTvShowRepositoryTest
import ru.mikhailskiy.intensiv.domain.repository.FavoriteMovieRepository
import ru.mikhailskiy.intensiv.domain.repository.TvShowsRepository
import ru.mikhailskiy.intensiv.domain.usecase.tvshows.TvShowsUseCases
import ru.mikhailskiy.intensiv.domain.usecase.watchlist.WatchlistUseCases
import ru.mikhailskiy.intensiv.presentation.tvshows.TvShowsViewModel
import ru.mikhailskiy.intensiv.presentation.watchlist.WatchlistViewModel

@OptIn(KoinApiExtension::class)
val movieAppTestModule = module {

    // TvShows
    viewModel { TvShowsViewModel() }
    single { TvShowsUseCases() }

    // Watchlist
    viewModel { WatchlistViewModel() }
    single { WatchlistUseCases() }


    // Repositories
    single<TvShowsRepository> { PopularTvShowRepositoryTest() }
    single<FavoriteMovieRepository> { FavoriteMovieRepositoryTest() }

}