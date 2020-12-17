package ru.mikhailskiy.intensiv.data

interface RatingProvider {
    val voteAverage: Double
    val rating: Float
        get() = voteAverage.div(2).toFloat()
}