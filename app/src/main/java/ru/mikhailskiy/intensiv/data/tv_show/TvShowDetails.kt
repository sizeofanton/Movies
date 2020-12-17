package ru.mikhailskiy.intensiv.data.tv_show

import com.google.gson.annotations.SerializedName
import ru.mikhailskiy.intensiv.data.BackdropProvider
import ru.mikhailskiy.intensiv.data.PosterProvider
import ru.mikhailskiy.intensiv.data.RatingProvider
import ru.mikhailskiy.intensiv.data.common.Genre
import ru.mikhailskiy.intensiv.data.common.ProductionCompany
import ru.mikhailskiy.intensiv.data.common.ProductionCountry
import ru.mikhailskiy.intensiv.data.common.SpokenLanguage

data class TvShowDetails(
    @SerializedName("backdrop_path")
    override val backdropPath: String?,
    @SerializedName("created_by")
    val createdBy: List<Creator>,
    @SerializedName("episode_run_time")
    val episodesRunTime: List<Int>,
    @SerializedName("first_air_date")
    val firstAirDate: String,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    @SerializedName("in_production")
    val inProduction: Boolean,
    val languages: List<String>,
    @SerializedName("last_air_date")
    val lastAirDate: String,
    @SerializedName("last_episode_to_air")
    val lastEpisodeToAir: Episode,
    @SerializedName("name")
    val title: String,
    @SerializedName("next_episode_to_air")
    val nextEpisodeToAir: Episode?,
    val networks: List<Network>,
    @SerializedName("number_of_episodes")
    val numberOfEpisodes: Int,
    @SerializedName("number_of_seasons")
    val numberOfSeasons: Int,
    @SerializedName("origin_country")
    val originCountry: List<String>,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_name")
    val originalName: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    override val posterPath: String?,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany>,
    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountry>,
    @SerializedName("seasons")
    val seasons: List<Season>,
    @SerializedName("spoken_languages")
    val spokenLanguage: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val type: String,
    @SerializedName("vote_average")
    override val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
): BackdropProvider, RatingProvider, PosterProvider