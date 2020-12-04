package ru.mikhailskiy.intensiv.data.common

import com.google.gson.annotations.SerializedName

data class SpokenLanguage(
    @SerializedName("iso_639_1")
    val isoCode: String,
    val name: String
)
