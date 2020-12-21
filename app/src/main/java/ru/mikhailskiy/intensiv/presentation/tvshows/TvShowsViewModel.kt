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
import timber.log.Timber

@KoinApiExtension
class TvShowsViewModel: ViewModel(), KoinComponent {

    private val useCases: TvShowsUseCases by inject()
    private val subscriptions: CompositeDisposable = CompositeDisposable()

    private val _tvShows = MutableLiveData<List<TvShow>>()
    val tvShowsLiveData: LiveData<List<TvShow>>
        get() = _tvShows

    private val _errorMessage = MutableLiveData<String>()
    val errorMessageLiveData: LiveData<String>
        get() = _errorMessage

    private val _progressBarVisibility = MutableLiveData<Int>(View.VISIBLE)
    val progressBarVisibility: LiveData<Int>
        get() = _progressBarVisibility

    fun fetchTvShows() {
        subscriptions.add(
            useCases.getPopularTvShows()
                .doOnTerminate { _progressBarVisibility.value = View.INVISIBLE}
                .subscribe({ tvShows ->
                    _tvShows.value = tvShows
                }, { throwable ->
                    Timber.e(throwable)
                    _errorMessage.value = throwable.message
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }
}