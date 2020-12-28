package ru.mikhailskiy.intensiv.di

import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module
import ru.mikhailskiy.intensiv.data.repository.FavoriteMovieRepositoryLocal
import ru.mikhailskiy.intensiv.data.repository.FavoriteMovieRepositoryTest
import ru.mikhailskiy.intensiv.data.repository.PopularTvShowRepository
import ru.mikhailskiy.intensiv.data.repository.PopularTvShowRepositoryTest
import ru.mikhailskiy.intensiv.data.room.AppDatabase
import ru.mikhailskiy.intensiv.domain.repository.FavoriteMovieRepository
import ru.mikhailskiy.intensiv.domain.repository.TvShowsRepository
import ru.mikhailskiy.intensiv.domain.usecase.movie_details.MovieDetailsUseCases
import ru.mikhailskiy.intensiv.domain.usecase.tvshows.TvShowsUseCases
import ru.mikhailskiy.intensiv.domain.usecase.watchlist.WatchlistUseCases
import ru.mikhailskiy.intensiv.presentation.movie_details.MovieDetailsViewModel
import ru.mikhailskiy.intensiv.presentation.tvshows.TvShowsViewModel
import ru.mikhailskiy.intensiv.presentation.watchlist.WatchlistViewModel
import ru.mikhailskiy.intensiv.util.DataLoadingStates

@OptIn(KoinApiExtension::class)
val movieAppPresentationModuleTest = module {
    // TvShows
    viewModel { TvShowsViewModel() }
    single { TvShowsUseCases() }

    // Watchlist
    viewModel { WatchlistViewModel() }
    single { WatchlistUseCases() }

    // MovieDetails
    viewModel { MovieDetailsViewModel() }
    single { MovieDetailsUseCases() }

    factory { CompositeDisposable() }
    factory<MutableLiveData<DataLoadingStates>> { MutableLiveData() }
    single<AppDatabase> { AppDatabase.newInstance(androidContext())}
}

val movieAppDomainModuleTest = module {
    single<TvShowsRepository> { PopularTvShowRepositoryTest() }
    single<FavoriteMovieRepository> { FavoriteMovieRepositoryTest() }
}