package ru.mikhailskiy.intensiv.presentation.tvshows

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.mikhailskiy.intensiv.data.vo.TvShow
import ru.mikhailskiy.intensiv.domain.usecase.tvshows.TvShowsUseCases
import ru.mikhailskiy.intensiv.extension.useDefaultNetworkThreads
import ru.mikhailskiy.intensiv.util.DataLoadingStates
import timber.log.Timber
import java.lang.Exception

@KoinApiExtension
class TvShowsViewModel: ViewModel(), KoinComponent {

    private val useCases: TvShowsUseCases by inject()
    private val subscriptions: CompositeDisposable by inject()

    private val _tvShows = MutableLiveData<List<TvShow>>()
    val tvShowsLiveData: LiveData<List<TvShow>>
        get() = _tvShows

    private val _loadingState: MutableLiveData<DataLoadingStates> by inject()
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

    fun fetchTvShowsCoroutine() {
        viewModelScope.launch {
            try {
                _loadingState.value = DataLoadingStates.Loading
                val tvShows = useCases.getPopularTvShowsCoroutines()
                _tvShows.value = tvShows
                _loadingState.value = DataLoadingStates.Loaded
            } catch (e: Exception)  {
                _loadingState.value = DataLoadingStates.Error
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }
}