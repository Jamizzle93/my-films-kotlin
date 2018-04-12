package com.mysticwater.myfilms.data.source

import com.mysticwater.myfilms.data.Film

enum class FilmType {
    NOW_SHOWING,
    UPCOMING,
    FAVOURITES
}

interface FilmsDataSource {

    interface LoadFilmsCallback {

        fun onFilmsLoaded(films: List<Film>)

        fun onDataNotAvailable()

    }

    interface GetFilmCallback {

        fun onFilmLoaded(film: Film)

        fun onDataNotAvailable()

    }

    fun getFilms(filmType: FilmType, callback: LoadFilmsCallback)

    fun getFilm(filmId: Int, callback: GetFilmCallback)

    fun saveFilm(film: Film)

    fun deleteAllFilms(filmType: FilmType)
}