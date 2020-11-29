package ru.mikhailskiy.intensiv.data

open class Movie(
    open var title: String? = "",
    open var voteAverage: Double = 0.0
) {
    val rating: Float
        get() = voteAverage.div(2).toFloat()
}
