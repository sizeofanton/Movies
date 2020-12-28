package ru.mikhailskiy.intensiv.util

import ru.mikhailskiy.intensiv.data.dto.common.Genre

object GenreParser {
        fun parse(genres: List<Genre>): String =
            genres.fold("") { acc, genre ->
                acc + "${genre.name}, "
            }
                .dropLast(2)
}