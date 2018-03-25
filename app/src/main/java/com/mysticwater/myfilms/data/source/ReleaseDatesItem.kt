package com.mysticwater.myfilms.data.source


import com.squareup.moshi.Json

data class ReleaseDatesItem(

	@Json(name="release_date")
	val releaseDate: String? = null,

	@Json(name="type")
	val type: Int? = null,

	@Json(name="iso_639_1")
	val iso6391: String? = null,

	@Json(name="certification")
	val certification: String? = null
)