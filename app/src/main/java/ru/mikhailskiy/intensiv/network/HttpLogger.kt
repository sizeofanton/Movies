package ru.mikhailskiy.intensiv.network

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

private const val LOGGING_PREFIX = "LoggingInterceptor::"
class HttpLogger: HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        if (!message.startsWith("{")) {
            Timber.d("$LOGGING_PREFIX $message")
            return
        }
        try {
            val prettyPrint = GsonBuilder()
                .setPrettyPrinting()
                .create()
                .toJson(JsonParser().parse(message))
            Timber.d("$LOGGING_PREFIX $prettyPrint")
        } catch (e: JsonSyntaxException) {
            Timber.d("$LOGGING_PREFIX $message")
        }
    }
}