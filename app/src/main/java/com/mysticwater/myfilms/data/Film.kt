package com.mysticwater.myfilms.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity

@Entity(tableName = "films")
data class Film constructor(
        @ColumnInfo(name = "id") var id: Int = 0,
        @ColumnInfo(name = "title") var title: String = "",
        @ColumnInfo(name = "poster_path") var poster_path: String = "",
        @ColumnInfo(name = "release_date") var release_date: String = "",
        @ColumnInfo(name = "runtime") var runtime: Int = 0,
        @ColumnInfo(name = "overview") var overview: String = "",
        @ColumnInfo(name = "backdrop_path") var backdrop_path: String = "",
        @ColumnInfo(name = "imdb_id") var imdb_id: String = "",
        @ColumnInfo(name = "tagline") var tagline: String = ""
) : Comparable<Film> {

    override fun compareTo(other: Film) = when {
        release_date < other.release_date -> -1
        release_date > other.release_date -> 1
        else -> 0
    }
}