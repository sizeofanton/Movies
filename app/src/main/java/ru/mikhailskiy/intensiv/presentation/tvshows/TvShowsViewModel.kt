package ru.mikhailskiy.intensiv.presentation.tvshows

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.mikhailskiy.intensiv.data.vo.TvShow
import ru.mikhailskiy.intensiv.domain.usecase.tvshows.TvShowsUseCases
import ru.mikhailskiy.intensiv.extension.useDefaultNetworkThreads
import timber.log.Timber

@KoinApiExtension
class TvShowsViewModel: ViewModel(), KoinComponent {

    private val useCases: TvShowsUseCases by inject()
    private val subscriptions: CompositeDisposable = CompositeDisposable()

    private val _tvShows = MutableLiveData<List<TvShow>>()
    val tvShowsLiveData: LiveData<List<TvShow>>
        get() = _tvShows

    private val _loadingState = MutableLiveData<DataLoadingStates>()
    val loadingStateLiveData: LiveData<DataLoadingStates> = _loadingState

    fun fetchTvShows() {
        subscriptions.add(
            useCases.getPopularTvShows()
                .useDefaultNetworkThreads()
                .doOnSubscribe { _loadingState.value = DataLoadingStates.Loading }
                .subscribe({ tvShows ->
                    _tvShows.value = tvShows
                    _loadingState.value = DataLoadingStates.Loaded
                }, { throwable ->
                    Timber.e(throwable)
                    _loadingState.value = DataLoadingStates.Error
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }

    enum class DataLoadingStates() {
        Loading, Error, Loaded
    }
}