package ru.mikhailskiy.intensiv.data.dto.tv_show

import com.google.gson.annotations.SerializedName

data class TvShowResponse(
        val page: Int,
        val results: List<TvShowDto>,
        @SerializedName("total_results")
    val totalResults: Int,
        @SerializedName("total_pages")
    val totalPages: Int
)