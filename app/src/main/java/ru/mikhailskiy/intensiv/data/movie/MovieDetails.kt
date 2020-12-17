package ru.mikhailskiy.intensiv.data.movie

import com.google.gson.annotations.SerializedName
import ru.mikhailskiy.intensiv.data.BackdropProvider
import ru.mikhailskiy.intensiv.data.PosterProvider
import ru.mikhailskiy.intensiv.data.RatingProvider
import ru.mikhailskiy.intensiv.data.common.Genre
import ru.mikhailskiy.intensiv.data.common.ProductionCompany
import ru.mikhailskiy.intensiv.data.common.ProductionCountry
import ru.mikhailskiy.intensiv.data.common.SpokenLanguage

data class MovieDetails(
    val adult: Boolean,
    @SerializedName("backdrop_path")
    override val backdropPath: String,
    @SerializedName("belongs_to_collection")
    val belongsToCollection: List<MovieDetails>?,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String?,
    val id: Int,
    @SerializedName("imdb_id")
    val imdbId: String?,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    val overview: String?,
    val popularity: Double,
    @SerializedName("poster_path")
    override val posterPath: String?,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany>,
    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountry>,
    @SerializedName("release_date")
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int?,
    @SerializedName("spoken_language")
    val spokenLanguages: List<SpokenLanguage>,
    val status: String,
    val tagline: String?,
    val title: String,
    val video: Boolean,
    @SerializedName("vote_average")
    override val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
): BackdropProvider, PosterProvider, RatingProvider

