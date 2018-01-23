package com.mysticwater.myfilms.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity

@Entity(tableName = "films")
data class Film constructor(
        @ColumnInfo(name = "id") var id: Int = 0,
        @ColumnInfo(name = "title") var title: String = "",
        @ColumnInfo(name = "poster_path") var poster_path: String? = "",
        @ColumnInfo(name = "release_date") var release_date: String = "") : Comparable<Film> {


    override fun compareTo(other: Film) = when {
        title < other.title -> -1
        title > other.title -> 1
        else -> 0
    }
}