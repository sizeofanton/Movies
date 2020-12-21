package ru.mikhailskiy.intensiv.data.dto.common

import com.google.gson.annotations.SerializedName

data class ProductionCountry(
    @SerializedName("iso_3166_1")
    val isoCode: String,
    val name: String
)
