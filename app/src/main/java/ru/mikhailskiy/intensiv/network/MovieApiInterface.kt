package ru.mikhailskiy.intensiv.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.mikhailskiy.intensiv.MovieFinderApp
import ru.mikhailskiy.intensiv.data.movie.MovieResponse
import ru.mikhailskiy.intensiv.data.tv_show.TvShowResponse
import ru.mikhailskiy.intensiv.data.movie.MovieDetails
import ru.mikhailskiy.intensiv.data.movie_credits.MovieCredits
import ru.mikhailskiy.intensiv.data.tv_show.TvShowDetails


interface MovieApiInterface {
    @GET("tv/popular")
    fun getPopularShow(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = MovieFinderApp.locale
    ): Call<TvShowResponse>

    @GET("tv/{id}")
    fun getShowDescription(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = MovieFinderApp.locale
    ): Call<TvShowDetails>

    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = MovieFinderApp.locale
    ): Call<MovieResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = MovieFinderApp.locale
    ): Call<MovieResponse>

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = MovieFinderApp.locale
    ): Call<MovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = MovieFinderApp.locale
    ): Call<MovieDetails>

    @GET("movie/{movie_id}/credits")
    fun getMovieCredits(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = MovieFinderApp.locale
    ): Call<MovieCredits>

}
