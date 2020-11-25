package ru.mikhailskiy.intensiv.data

object MockRepository {

    fun getMovies(): List<Movie> {

        val moviesList = mutableListOf<Movie>()
        for (x in 0..10) {
            val movie = Movie(
                title = "Spider-Man $x",
                voteAverage = 10.0 - x
            )
            moviesList.add(movie)
        }

        return moviesList
    }

    fun getTvShows(): List<Movie> {
        val showsList = mutableListOf<Movie>()
        for (x in 1..32) {
            val show = Movie(
                title = "Simpsons $x",
                voteAverage = 10.0 - x / 2
            )
            showsList.add(show)
        }
        return showsList
    }

}