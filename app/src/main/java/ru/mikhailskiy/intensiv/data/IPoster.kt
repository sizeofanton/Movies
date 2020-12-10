package ru.mikhailskiy.intensiv.data

import ru.mikhailskiy.intensiv.BuildConfig

interface IPoster {
    val posterPath: String?
    fun getPoster(): String = "${BuildConfig.API_IMAGE_URL}$posterPath"
}