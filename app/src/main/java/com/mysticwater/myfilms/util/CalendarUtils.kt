package com.mysticwater.myfilms.util

import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object CalendarUtils {

    private val LOG_TAG = "CalendarUtils"
    private val MOVIE_DB_DATE_FORMAT = "yyyy-MM-dd"

    fun stringToCalendar(dateStr: String): Calendar? {
        var result: Calendar? = null
        try {
            val sdf = SimpleDateFormat(MOVIE_DB_DATE_FORMAT, Locale.UK)
            val calendar = Calendar.getInstance()
            calendar.time = sdf.parse(dateStr)
            result = calendar
        } catch (parseException: ParseException) {
            Log.e(
                    LOG_TAG,
                    "Could not parse '" + dateStr + "' to a calendar object. " +
                            "Exception: " + parseException.message
            )
        }

        return result
    }

    fun calendarToString(date: Calendar?): String {
        if (date == null) return ""
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.UK)
        return sdf.format(date.time)
    }


}
