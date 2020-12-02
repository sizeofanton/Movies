package ru.mikhailskiy.intensiv

import org.junit.Test
import ru.mikhailskiy.intensiv.util.DateParser
import org.junit.Assert.*

class DateParserTest {
    @Test
    fun test() {
        val interval = DateParser.getYearInterval(
            "1992-02-02",
            "1993-02-02",
            DateParser.TheMovieDbFormat
        )
        assertEquals("1992 - 1993", interval)
    }

    @Test
    fun testSameYear() {
        val year = DateParser.getYearInterval(
            "1992-02-02",
            "1992-03-03",
            DateParser.TheMovieDbFormat
        )
        assertEquals("1992", year)
    }

    @Test
    fun singleDateTest() {
        val year = DateParser.getYear(
            "1992-02-03",
            DateParser.TheMovieDbFormat
        )
        assertEquals("1992", year)
    }
}