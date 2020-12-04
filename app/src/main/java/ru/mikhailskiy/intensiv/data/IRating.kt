package ru.mikhailskiy.intensiv.data

interface IRating {
    val voteAverage: Double
    val rating: Float
        get() = voteAverage.div(2).toFloat()
}