package com.mysticwater.myfilms.data.source.remote

import com.mysticwater.myfilms.data.Film
import com.mysticwater.myfilms.data.source.FilmsDataSource
import com.mysticwater.myfilms.network.TheMovieDbService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FilmsRemoteDataSource : FilmsDataSource {

    private val FAKE_FILMS_DATA = LinkedHashMap<Int, Film>(2)

    init {
        val film1 = Film(1, "Film one")
        val film2 = Film(2, "Film two")
        FAKE_FILMS_DATA.put(film1.id, film1)
        FAKE_FILMS_DATA.put(film2.id, film2)
    }

    override fun getFilms(callback: FilmsDataSource.LoadFilmsCallback) {
        val tmdbService = TheMovieDbService.getTmdbService()
        tmdbService.getUpcomingReleases("gb", "2017-08-22", "2017-09-24")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    callback.onFilmsLoaded(result.results)
                }, { error ->
                    // TODO - Handle error
                    error.printStackTrace()
                })
    }

    companion object {

        private lateinit var INSTANCE: FilmsRemoteDataSource

        private var needNewInstance = true

        @JvmStatic
        fun getInstance(): FilmsRemoteDataSource {
            if (needNewInstance) {
                INSTANCE = FilmsRemoteDataSource()
                needNewInstance = false
            }
            return INSTANCE
        }
    }

}