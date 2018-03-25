package com.mysticwater.myfilms.data.source


import com.squareup.moshi.Json

data class ProductionCountriesItem(

	@Json(name="iso_3166_1")
	val iso31661: String? = null,

	@Json(name="name")
	val name: String? = null
)