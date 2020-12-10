package ru.mikhailskiy.intensiv.extension

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.useDefaultNetworkThreads(): Single<T> {
    return this.subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
}
