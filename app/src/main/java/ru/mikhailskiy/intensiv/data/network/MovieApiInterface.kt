package ru.mikhailskiy.intensiv.data.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.mikhailskiy.intensiv.BuildConfig
import ru.mikhailskiy.intensiv.MovieFinderApp
import ru.mikhailskiy.intensiv.data.dto.movie.MovieDetailsDto
import ru.mikhailskiy.intensiv.data.dto.movie.MovieResponse
import ru.mikhailskiy.intensiv.data.dto.movie_credits.MovieCreditsDto
import ru.mikhailskiy.intensiv.data.dto.tv_show.TvShowDetailsDto
import ru.mikhailskiy.intensiv.data.dto.tv_show.TvShowResponse

private const val API_KEY = BuildConfig.THE_MOVIE_DATABASE_API
interface MovieApiInterface {
    @GET("tv/popular")
    fun getPopularShow(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = MovieFinderApp.locale
    ): Single<TvShowResponse>

    @GET("tv/{id}")
    fun getShowDescription(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = MovieFinderApp.locale
    ): Single<TvShowDetailsDto>

    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = MovieFinderApp.locale
    ): Single<MovieResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = MovieFinderApp.locale
    ): Single<MovieResponse>

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = MovieFinderApp.locale
    ): Single<MovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = MovieFinderApp.locale
    ): Single<MovieDetailsDto>

    @GET("movie/{movie_id}/credits")
    fun getMovieCredits(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = MovieFinderApp.locale
    ): Single<MovieCreditsDto>

    @GET("search/movie")
    fun searchMovies(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = MovieFinderApp.locale
    ): Single<MovieResponse>

    @GET("tv/popular")
    suspend fun getPopularShowCoroutine(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = MovieFinderApp.locale
    ): TvShowResponse
}
