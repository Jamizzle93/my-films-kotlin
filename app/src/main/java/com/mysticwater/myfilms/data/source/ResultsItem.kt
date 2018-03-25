package com.mysticwater.myfilms.data.source


import com.squareup.moshi.Json

data class ResultsItem(

	@Json(name="release_dates")
	val releaseDates: List<ReleaseDatesItem?>? = null,

	@Json(name="iso_3166_1")
	val iso31661: String? = null
)