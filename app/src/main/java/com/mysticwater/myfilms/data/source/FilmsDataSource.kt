package com.mysticwater.myfilms.data.source

import com.mysticwater.myfilms.data.Film

interface FilmsDataSource {

    interface LoadFilmsCallback {

        fun onFilmsLoaded(films: List<Film>)

        fun onDataNotAvailable()

    }

    fun getFilms(callback: LoadFilmsCallback)
}