package ru.mikhailskiy.intensiv.data.tv_show

import com.google.gson.annotations.SerializedName

data class TvShowResponse(
    val page: Int,
    val results: List<TvShow>,
    @SerializedName("total_results")
    val totalResults: Int,
    @SerializedName("total_pages")
    val totalPages: Int
)