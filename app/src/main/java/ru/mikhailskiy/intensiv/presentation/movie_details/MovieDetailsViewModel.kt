package ru.mikhailskiy.intensiv.presentation.movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.mikhailskiy.intensiv.data.room.AppDatabase
import ru.mikhailskiy.intensiv.data.room.entity.FavoriteMovieEntity
import ru.mikhailskiy.intensiv.data.vo.MovieDetails
import ru.mikhailskiy.intensiv.data.vo.TvShowDetails
import ru.mikhailskiy.intensiv.domain.usecase.movie_details.MovieDetailsUseCases
import ru.mikhailskiy.intensiv.extension.useDefaultDatabaseThreads
import ru.mikhailskiy.intensiv.extension.useDefaultNetworkThreads
import ru.mikhailskiy.intensiv.util.DataLoadingStates

@KoinApiExtension
class MovieDetailsViewModel: ViewModel(), KoinComponent {

    private val useCases: MovieDetailsUseCases by inject()
    private val subscriptions: CompositeDisposable by inject()
    private val database: AppDatabase by inject()

    private val _tvShowDetails: MutableLiveData<TvShowDetails> = MutableLiveData()
    val tvShowDetails: LiveData<TvShowDetails>
        get() = _tvShowDetails
    private val _movieDetails: MutableLiveData<MovieDetails> = MutableLiveData()
    val movieDetails: LiveData<MovieDetails>
        get() = _movieDetails
    private val _loadingState = MutableLiveData<DataLoadingStates>()
    val loadingState: LiveData<DataLoadingStates>
        get() = _loadingState
    private val _movieFavorite: MutableLiveData<Boolean> = MutableLiveData()
    val movieFavorite: LiveData<Boolean>
        get() = _movieFavorite

    fun getTvShowDetails(id: Int) {
        subscriptions.add(
            useCases.getTvShowDetails(id)
                .useDefaultNetworkThreads()
                .doOnSubscribe { _loadingState.value = DataLoadingStates.Loading }
                .subscribe({ details ->
                    _tvShowDetails.value = details
                    _loadingState.value = DataLoadingStates.Loaded
                }, {
                    _loadingState.value = DataLoadingStates.Error
                })
        )
    }

    fun getMovieDetails(id: Int) {
        subscriptions.add(
            useCases.getMovieDetails(id)
                .useDefaultNetworkThreads()
                .doOnSubscribe { _loadingState.value = DataLoadingStates.Loading }
                .subscribe({ details ->
                    _movieDetails.value = details
                    _loadingState.value = DataLoadingStates.Loaded
                }, {
                    _loadingState.value = DataLoadingStates.Error
                })
        )
    }

    fun checkIfMovieIsFavorite(id: Int) {
        subscriptions.add(
            database.favorites().exists(id)
                .useDefaultDatabaseThreads()
                .subscribe { exists ->
                    _movieFavorite.value = exists
                }
        )
    }

    fun addToFavorites(id: Int, poster: String) {
        subscriptions.add(
            database.favorites()
                .insert(FavoriteMovieEntity(poster, id))
                .subscribeOn(Schedulers.io())
                .subscribe()
        )
    }

    fun removeFromFavorites(id: Int, poster: String) {
        subscriptions.add(
            database.favorites()
                .delete(FavoriteMovieEntity(poster, id))
                .subscribeOn(Schedulers.io())
                .subscribe()
        )
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }
}