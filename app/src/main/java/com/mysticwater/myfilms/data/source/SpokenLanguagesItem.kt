package com.mysticwater.myfilms.data.source


import com.squareup.moshi.Json

data class SpokenLanguagesItem(

	@Json(name="name")
	val name: String? = null,

	@Json(name="iso_639_1")
	val iso6391: String? = null
)