package ru.mikhailskiy.intensiv.data.dto.tv_show

import com.google.gson.annotations.SerializedName

data class Network(
    val name: String,
    val id: Int,
    @SerializedName("logo_path")
    val logoPath: String,
    @SerializedName("origin_country")
    val originCountry: String
)
