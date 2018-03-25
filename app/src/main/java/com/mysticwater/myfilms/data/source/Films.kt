package com.mysticwater.myfilms.data.source


import com.squareup.moshi.Json

data class Films(

	@Json(name="original_language")
	val originalLanguage: String? = null,

	@Json(name="imdb_id")
	val imdbId: String? = null,

	@Json(name="video")
	val video: Boolean? = null,

	@Json(name="title")
	val title: String? = null,

	@Json(name="backdrop_path")
	val backdropPath: String? = null,

	@Json(name="revenue")
	val revenue: Int? = null,

	@Json(name="genres")
	val genres: List<GenresItem?>? = null,

	@Json(name="popularity")
	val popularity: Double? = null,

	@Json(name="release_dates")
	val releaseDates: ReleaseDates? = null,

	@Json(name="production_countries")
	val productionCountries: List<ProductionCountriesItem?>? = null,

	@Json(name="id")
	val id: Int? = null,

	@Json(name="vote_count")
	val voteCount: Int? = null,

	@Json(name="budget")
	val budget: Int? = null,

	@Json(name="overview")
	val overview: String? = null,

	@Json(name="original_title")
	val originalTitle: String? = null,

	@Json(name="runtime")
	val runtime: Int? = null,

	@Json(name="poster_path")
	val posterPath: String? = null,

	@Json(name="spoken_languages")
	val spokenLanguages: List<SpokenLanguagesItem?>? = null,

	@Json(name="production_companies")
	val productionCompanies: List<ProductionCompaniesItem?>? = null,

	@Json(name="release_date")
	val releaseDate: String? = null,

	@Json(name="vote_average")
	val voteAverage: Double? = null,

	@Json(name="belongs_to_collection")
	val belongsToCollection: BelongsToCollection? = null,

	@Json(name="tagline")
	val tagline: String? = null,

	@Json(name="adult")
	val adult: Boolean? = null,

	@Json(name="homepage")
	val homepage: String? = null,

	@Json(name="status")
	val status: String? = null
)