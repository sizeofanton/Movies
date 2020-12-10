package ru.mikhailskiy.intensiv.data.movie

import com.google.gson.annotations.SerializedName
import ru.mikhailskiy.intensiv.data.IBackdrop
import ru.mikhailskiy.intensiv.data.IPoster
import ru.mikhailskiy.intensiv.data.IRating
import ru.mikhailskiy.intensiv.data.common.Genre

data class Movie(
    @SerializedName("poster_path")
    override val posterPath: String?,
    val adult: Boolean,
    val overview: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("genres", alternate = ["genres_ids"])
    val genres: List<Genre>,
    val id: Int,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("original_language")
    val originalLanguage: String,
    val title: String,
    @SerializedName("backdrop_path")
    override val backdropPath: String,
    val popularity: Double,
    @SerializedName("vote_count")
    val voteCount: Int,
    val video: Boolean,
    @SerializedName("vote_average")
    override val voteAverage: Double,
    val homepage: String
): IPoster, IBackdrop, IRating
