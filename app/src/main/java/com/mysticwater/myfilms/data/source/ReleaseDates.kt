package com.mysticwater.myfilms.data.source


import com.squareup.moshi.Json

data class ReleaseDates(

	@Json(name="results")
	val results: List<ResultsItem?>? = null
)