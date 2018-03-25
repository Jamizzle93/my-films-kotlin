package com.mysticwater.myfilms.data.source


import com.squareup.moshi.Json

data class ProductionCompaniesItem(

	@Json(name="logo_path")
	val logoPath: Any? = null,

	@Json(name="name")
	val name: String? = null,

	@Json(name="id")
	val id: Int? = null,

	@Json(name="origin_country")
	val originCountry: String? = null
)