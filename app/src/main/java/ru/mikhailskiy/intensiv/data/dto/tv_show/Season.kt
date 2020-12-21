package ru.mikhailskiy.intensiv.data.dto.tv_show

import com.google.gson.annotations.SerializedName
import ru.mikhailskiy.intensiv.data.dto.PosterProvider

data class Season(
    @SerializedName("air_date")
    val airDate: String,
    @SerializedName("episodes_count")
    val episodesCount: Int,
    val id: Int,
    val name: String,
    val overview: String,
    @SerializedName("poster_path")
    override val posterPath: String,
    @SerializedName("season_number")
    val seasonNumber: Int
): PosterProvider
