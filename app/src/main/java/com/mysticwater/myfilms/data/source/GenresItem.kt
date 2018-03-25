package com.mysticwater.myfilms.data.source


import com.squareup.moshi.Json

data class GenresItem(

	@Json(name="name")
	val name: String? = null,

	@Json(name="id")
	val id: Int? = null
)