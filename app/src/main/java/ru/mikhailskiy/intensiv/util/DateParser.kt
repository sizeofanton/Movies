package ru.mikhailskiy.intensiv.util

import java.text.SimpleDateFormat

private const val START_YEAR = 1900
class DateParser {
    companion object {
        const val TheMovieDbFormat = "yyyy-MM-dd"
        fun getYearInterval(
            dateOne: String,
            dateTwo: String,
            format: String
        ): String {
            val yearOne = SimpleDateFormat(format).parse(dateOne).year + START_YEAR
            val yearTwo = SimpleDateFormat(format).parse(dateTwo).year + START_YEAR
            return if (yearOne == yearTwo) "$yearOne"
            else "$yearOne - $yearTwo"
        }

        fun getYear(date: String, format: String): String
            = (SimpleDateFormat(format).parse(date).year + START_YEAR).toString()
    }
}