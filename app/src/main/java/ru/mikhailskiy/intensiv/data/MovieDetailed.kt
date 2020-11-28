package ru.mikhailskiy.intensiv.data

class MovieDetailed(
    override var title: String? = "",
    override var voteAverage: Double = 0.0,
    var description: String? = "",
    var posterUrl: String? = "",
    var studioName: String? = "",
    var genre: String? = "",
    var year: String? = "",
    var actorsList: List<Actor>? = listOf()
): Movie(title, voteAverage)