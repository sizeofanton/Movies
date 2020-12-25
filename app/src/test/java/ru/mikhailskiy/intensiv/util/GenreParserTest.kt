package ru.mikhailskiy.intensiv.util

import org.junit.Test
import org.junit.Assert.assertEquals
import ru.mikhailskiy.intensiv.data.dto.common.Genre

class GenreParserTest {
    @Test
    fun test() {
        val genresList = mutableListOf<Genre>().apply {
            add(Genre(1, "Western"))
            add(Genre(2, "Sci-Fi"))
            add(Genre(3, "Comedy"))
        }
        val expected = "Western, Sci-Fi, Comedy"
        assertEquals(expected, GenreParser.parse(genresList))
    }
}