package ru.mikhailskiy.intensiv.data.dto.common

import com.google.gson.annotations.SerializedName

data class ProductionCompany(
    val name: String,
    val id: Int,
    @SerializedName("logo_path")
    val logoPath: String?,
    @SerializedName("origin_country")
    val originCountry: String
)
