package ru.mikhailskiy.intensiv.data.tv_show

import com.google.gson.annotations.SerializedName
import ru.mikhailskiy.intensiv.data.BackdropProvider
import ru.mikhailskiy.intensiv.data.PosterProvider
import ru.mikhailskiy.intensiv.data.RatingProvider

data class TvShow(
    @SerializedName("poster_path")
    override val posterPath: String?,
    val popularity: Double,
    val id: Int,
    @SerializedName("backdrop_path")
    override val backdropPath: String?,
    @SerializedName("vote_average")
    override val voteAverage: Double,
    val overview: String,
    @SerializedName("first_air_date")
    val firstAirDate: String,
    @SerializedName("origin_country")
    val originCountry: List<String>,
    @SerializedName("genre_ids")
    val genreIds: List<Int>,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("vote_count")
    val voteCount: Int,
    @SerializedName("name")
    val title: String,
    @SerializedName("original_name")
    val originalTitle: String
): BackdropProvider, PosterProvider, RatingProvider