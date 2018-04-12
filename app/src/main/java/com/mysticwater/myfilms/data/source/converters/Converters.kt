package com.mysticwater.myfilms.data.source.converters

import android.arch.persistence.room.TypeConverter
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Converters {

    @TypeConverter
    fun fromDateString(calendarStr: String?): Calendar? {
        try {
            val calendar = Calendar.getInstance()
            calendar.time = MOVIE_DB_DATE_FORMAT.parse(calendarStr)
            return calendar
        } catch (e: ParseException) {
            return null
        }

    }

    @TypeConverter
    fun calendarToString(calendar: Calendar?): String? {
        return if (calendar == null) {
            null
        } else {
            MOVIE_DB_DATE_FORMAT.format(calendar.time)
        }
    }

    companion object {
        private val MOVIE_DB_DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd", Locale.UK)
    }

}