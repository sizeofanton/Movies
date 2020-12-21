package ru.mikhailskiy.intensiv.data.dto

class MockMovie(
    var title: String? = null,
    var voteAverage: Double = 0.0
) {
    val rating: Float
        get() = voteAverage.div(2).toFloat()
}
