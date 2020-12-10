package ru.mikhailskiy.intensiv.data

import ru.mikhailskiy.intensiv.BuildConfig

interface IBackdrop {
    val backdropPath: String?
    fun getBackdrop(): String = "${BuildConfig.API_IMAGE_URL}$backdropPath"
}