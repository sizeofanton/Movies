package ru.mikhailskiy.intensiv.data.dto.tv_show

import com.google.gson.annotations.SerializedName

data class Creator(
    val id: Int,
    @SerializedName("credit_id")
    val creditId: String,
    val name: String,
    val gender: Int,
    @SerializedName("profile_path")
    val profilePath: String?
)
