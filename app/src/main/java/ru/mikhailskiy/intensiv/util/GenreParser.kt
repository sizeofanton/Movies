package ru.mikhailskiy.intensiv.util

import ru.mikhailskiy.intensiv.data.common.Genre

class GenreParser {
    companion object {
        fun parse(genres: List<Genre>): String =
            genres.fold("") { acc, genre ->
                acc + "${genre.name}, "
            }
                .dropLast(2)
    }
}