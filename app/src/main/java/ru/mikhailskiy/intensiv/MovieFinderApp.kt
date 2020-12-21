package ru.mikhailskiy.intensiv

import android.app.Application
import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.mikhailskiy.intensiv.data.room.AppDatabase
import ru.mikhailskiy.intensiv.di.movieAppModule
import timber.log.Timber

class MovieFinderApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        appDatabase = AppDatabase.newInstance(instance as Context)
        initDebugTools()
        initDi()
    }
    private fun initDebugTools() {
        if (BuildConfig.DEBUG) {
            initTimber()
        }
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initDi() {
        startKoin {
            androidLogger()
            androidContext(this@MovieFinderApp)
            modules(movieAppModule)
        }
    }

    companion object {
        var instance: MovieFinderApp? = null
            private set
        lateinit var appDatabase: AppDatabase
            private set
        lateinit var locale: String
    }
}