package ru.mikhailskiy.intensiv.util

import java.text.SimpleDateFormat

class DateParser {
    companion object {
        const val TheMovieDbFormat = "yyyy-MM-dd"
        fun getYearInterval(
            dateOne: String,
            dateTwo: String,
            format: String
        ): String {
            val yearOne = SimpleDateFormat(format).parse(dateOne).year + 1900
            val yearTwo = SimpleDateFormat(format).parse(dateTwo).year + 1900
            return if (yearOne == yearTwo) "$yearOne"
            else "$yearOne - $yearTwo"
        }

        fun getYear(date: String, format: String): String
            = (SimpleDateFormat(format).parse(date).year + 1900).toString()
    }
}