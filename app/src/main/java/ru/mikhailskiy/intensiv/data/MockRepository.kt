package ru.mikhailskiy.intensiv.data

object MockRepository {

    fun getMovies(): List<MockMovie> {

        val moviesList = mutableListOf<MockMovie>()
        for (x in 0..10) {
            val movie = MockMovie(
                title = "Spider-Man $x",
                voteAverage = 10.0 - x
            )
            moviesList.add(movie)
        }

        return moviesList
    }

}