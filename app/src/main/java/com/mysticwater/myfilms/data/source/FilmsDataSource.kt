package com.mysticwater.myfilms.data.source

import com.mysticwater.myfilms.data.Film

interface FilmsDataSource {

    interface LoadFilmsCallback {

        fun onFilmsLoaded(films: List<Film>)

        fun onDataNotAvailable()

    }

    interface GetFilmCallback {

        fun onFilmLoaded(film: Film)

        fun onDataNotAvailable()

    }

    fun getNowShowingFilms(callback: LoadFilmsCallback)

    fun getFilm(filmId: Int, callback: GetFilmCallback)
}