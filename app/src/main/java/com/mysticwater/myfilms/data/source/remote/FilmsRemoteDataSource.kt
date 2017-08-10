package com.mysticwater.myfilms.data.source.remote

import com.google.common.collect.Lists
import com.mysticwater.myfilms.data.Film
import com.mysticwater.myfilms.data.source.FilmsDataSource

class FilmsRemoteDataSource : FilmsDataSource {

    private val FAKE_FILMS_DATA = LinkedHashMap<Int, Film>(2)

    init {
        val film1 = Film(1, "Film one")
        val film2 = Film(2, "Film two")
        FAKE_FILMS_DATA.put(film1.id, film1)
        FAKE_FILMS_DATA.put(film2.id, film2)
    }

    override fun getFilms(callback: FilmsDataSource.LoadFilmsCallback) {
        val films = Lists.newArrayList(FAKE_FILMS_DATA.values)
        callback.onFilmsLoaded(films)
    }

    companion object {

        private lateinit var INSTANCE: FilmsRemoteDataSource

        private var needNewInstance = true

        @JvmStatic fun getInstance(): FilmsRemoteDataSource {
            if (needNewInstance) {
                INSTANCE = FilmsRemoteDataSource()
                needNewInstance = false
            }
            return INSTANCE
        }
    }

}