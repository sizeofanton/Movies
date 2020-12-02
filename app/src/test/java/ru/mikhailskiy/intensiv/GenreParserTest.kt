package ru.mikhailskiy.intensiv

import org.junit.Test
import org.junit.Assert.assertEquals
import ru.mikhailskiy.intensiv.data.common.Genre
import ru.mikhailskiy.intensiv.util.GenreParser

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