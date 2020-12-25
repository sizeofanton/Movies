package ru.mikhailskiy.intensiv.presentation.watchlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.mikhailskiy.intensiv.data.vo.FavoriteMovie
import ru.mikhailskiy.intensiv.domain.usecase.watchlist.WatchlistUseCases
import ru.mikhailskiy.intensiv.extension.useDefaultDatabaseThreads

@KoinApiExtension
class WatchlistViewModel: ViewModel(), KoinComponent {

    private val subscriptions: CompositeDisposable = CompositeDisposable()
    private val useCases: WatchlistUseCases by inject()

    private val _favoriteMovies: MutableLiveData<List<FavoriteMovie>> = MutableLiveData()
    val favoriteMovies: LiveData<List<FavoriteMovie>>
        get() = _favoriteMovies
    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun getFavoriteMovies() {
        subscriptions.add(
            useCases.getFavoriteMovies()
                .useDefaultDatabaseThreads()
                .subscribe({ list ->
                        _favoriteMovies.value = list
                    },{ throwable ->
                        _errorMessage.value = throwable.message
                    })
        )
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }
}