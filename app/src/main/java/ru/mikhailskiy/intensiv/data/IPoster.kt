package ru.mikhailskiy.intensiv.data

import ru.mikhailskiy.intensiv.network.MovieApiClient

interface IPoster {
    val posterPath: String?
    fun getPoster(): String = "${MovieApiClient.IMAGE_BASE_URL}$posterPath"
}