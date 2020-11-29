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

    fun getMovieDetailed(): MovieDetailed {
        val title = "Aquaman"
        val rating = 8.0
        val description = """
            In 1985 Maine, lighthouse keeper Thomas Curry rescues Atlanna, the queen of the underwater nation of Atlantis, 
            during a storm. They eventually fall in love and have a son named Arthur, 
            who is born with the power to communicate with marine lifeforms. 
        """.trimIndent()
        val studioName = "Warner Bros."
        val genre = "Action, Adventure, Fantasy"
        val year = "2018"
        val posterUrl = "https://miro.medium.com/max/2560/1*2fuJeqIrzV13gs7HhixVPw.jpeg"
        val actors = mutableListOf<Actor>().apply {
            add(Actor(
                firstName = "Jason",
                lastName = "Momoa",
                photoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d6/Jason_Momoa_by_Gage_Skidmore.jpg/500px-Jason_Momoa_by_Gage_Skidmore.jpg"
            ))
            add(Actor(
                firstName = "Amber",
                lastName = "Heard",
                photoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4c/Amber_Heard_%2843723454772%29.jpg/480px-Amber_Heard_%2843723454772%29.jpg"
            ))
            add(Actor(
                firstName = "Patrick",
                lastName = "Wilson",
                photoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c1/Patrick_Wilson_by_Gage_Skidmore.jpg/440px-Patrick_Wilson_by_Gage_Skidmore.jpg"
            ))
            add(Actor(
                firstName = "Nicole",
                lastName = "Kidman",
                photoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/28/Nicole_Kidman_Cannes_2017_2.jpg/250px-Nicole_Kidman_Cannes_2017_2.jpg"
            ))
        }
        return MovieDetailed(
            title = title,
            voteAverage = rating,
            description = description,
            studioName = studioName,
            genre = genre,
            year = year,
            actorsList = actors,
            posterUrl = posterUrl
        )
    }

}