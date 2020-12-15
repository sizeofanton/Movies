package ru.mikhailskiy.intensiv.data

import ru.mikhailskiy.intensiv.BuildConfig

interface BackdropProvider {
    val backdropPath: String?
    fun getBackdrop(): String = "${BuildConfig.API_IMAGE_URL}$backdropPath"
}