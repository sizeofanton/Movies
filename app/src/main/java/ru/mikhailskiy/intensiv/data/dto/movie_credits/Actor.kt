package ru.mikhailskiy.intensiv.data.dto.movie_credits

data class Actor(
    val photoUrl: String,
    val firstName: String,
    val lastName: String
) {
    companion object {
        val placeholderUrl: String =
            "https://www.allianceplast.com/wp-content/uploads/2017/11/no-image.png</string>"
    }
}