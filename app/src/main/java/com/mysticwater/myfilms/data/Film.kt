package com.mysticwater.myfilms.data

import android.arch.persistence.room.*
import com.mysticwater.myfilms.data.source.converters.Converters
import java.util.*

@Entity(tableName = "Films")
@TypeConverters(Converters::class)
data class Film constructor(
        @PrimaryKey
        @ColumnInfo(name = "id") var id: Int = 0,
        @ColumnInfo(name = "title") var title: String = "",
        @ColumnInfo(name = "poster_path") var poster_path: String? = "",
        @ColumnInfo(name = "release_date") var release_date: Calendar? = null,
        @ColumnInfo(name = "runtime") var runtime: Int = 0,
        @ColumnInfo(name = "overview") var overview: String = "",
        @ColumnInfo(name = "backdrop_path") var backdrop_path: String? = "",
        @ColumnInfo(name = "imdb_id") var imdb_id: String = "",
        @ColumnInfo(name = "tagline") var tagline: String = "",
        @ColumnInfo(name = "vote_average") var vote_average: Float = 0.0f,
        @ColumnInfo(name = "vote_count") var vote_count: Int = 0,
        @ColumnInfo(name = "favourite") var favourite: Boolean = false
) : Comparable<Film> {

    override fun compareTo(other: Film) = when {
        release_date!! < other.release_date -> -1
        release_date!! > other.release_date -> 1
        else -> 0
    }
}