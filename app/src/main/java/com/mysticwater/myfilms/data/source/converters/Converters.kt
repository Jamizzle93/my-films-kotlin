package com.mysticwater.myfilms.data.source.converters

import android.arch.persistence.room.TypeConverter
import java.util.*

class Converters {

    @TypeConverter
    fun fromTimestamp(timestamp: Long?): Calendar? {
        return if (timestamp == null) {
            null
        } else {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = timestamp
            return calendar
        }
    }

    @TypeConverter
    fun toTimestamp(calendar: Calendar?): Long? {
        return if (calendar == null) {
            null
        } else {
            return calendar.timeInMillis
        }
    }
}