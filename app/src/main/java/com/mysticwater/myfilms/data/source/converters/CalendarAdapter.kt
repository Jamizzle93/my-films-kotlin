package com.mysticwater.myfilms.data.source.converters

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class CalendarAdapter {

    @ToJson
    internal fun toJson(calendar: Calendar): String {
        return MOVIE_DB_DATE_FORMAT.format(calendar.time)
    }

    @FromJson
    internal fun fromJson(calendarStr: String): Calendar? {
        try {
            val calendar = Calendar.getInstance()
            calendar.time = MOVIE_DB_DATE_FORMAT.parse(calendarStr)
            return calendar
        } catch (e: ParseException) {
            return null
        }

    }

    companion object {
        private val MOVIE_DB_DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd", Locale.UK)
    }

}