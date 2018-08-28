package com.mysticwater.myfilms.data

data class Film(
        val id: Long? = null,
        val title: String? = null,
        val posterPath: String? = null,
        val releaseDate: String? = null,
        val runtime: Int? = null,
        val overview: String? = null,
        val backdropPath: String? = null,
        val imdb_id: String? = null,
        val tagline: String? = null,
        val voteAverage: Double? = null,
        val voteCount: Int? = null
)