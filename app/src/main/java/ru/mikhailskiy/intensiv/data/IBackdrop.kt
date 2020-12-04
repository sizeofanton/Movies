package ru.mikhailskiy.intensiv.data

import ru.mikhailskiy.intensiv.network.MovieApiClient

interface IBackdrop {
    val backdropPath: String?
    fun getBackdrop(): String = "${MovieApiClient.IMAGE_BASE_URL}$backdropPath"
}