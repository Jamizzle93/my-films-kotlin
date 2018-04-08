package com.mysticwater.myfilms.data.source.remote

import com.mysticwater.myfilms.data.Film
import com.mysticwater.myfilms.data.source.FilmType
import com.mysticwater.myfilms.data.source.FilmsDataSource
import com.mysticwater.myfilms.network.TheMovieDbService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

class FilmsRemoteDataSource : FilmsDataSource {

    private val FAKE_FILMS_DATA = LinkedHashMap<Int, Film>(2)

    init {
        val film1 = Film(1, "Film one", "abc")
        val film2 = Film(2, "Film two", "abc")
        FAKE_FILMS_DATA.put(film1.id, film1)
        FAKE_FILMS_DATA.put(film2.id, film2)
    }

    override fun getFilms(filmType: FilmType, callback: FilmsDataSource.LoadFilmsCallback) {
        when (filmType) {
            FilmType.NOW_SHOWING -> getNowShowingFilms(callback)
            FilmType.UPCOMING -> getUpcomingFilms(callback)
            FilmType.FAVOURITES -> getFavouriteFilms(callback)
        }

    }

    override fun getFilm(filmId: Int, callback: FilmsDataSource.GetFilmCallback) {
        val tmdbService = TheMovieDbService.getTmdbService()
        tmdbService.getFilm(filmId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    callback.onFilmLoaded(result)
                }, { error ->
                    error.printStackTrace()
                })
    }

    fun getNowShowingFilms(callback: FilmsDataSource.LoadFilmsCallback) {
        val tmdbService = TheMovieDbService.getTmdbService()
        tmdbService.getNowPlaying("en-GB", "GB")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    callback.onFilmsLoaded(result.results)
                }, { error ->
                    // TODO - Handle error
                    println(error.message)
                    error.printStackTrace()
                })
    }

    fun getUpcomingFilms(callback: FilmsDataSource.LoadFilmsCallback) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd");

        val fromDate: Calendar = Calendar.getInstance()
        val fromDateStr = dateFormat.format(fromDate.time)
        fromDate.add(Calendar.DATE, 14)
        val toDateStr = dateFormat.format(fromDate.time)

        val tmdbService = TheMovieDbService.getTmdbService()
        tmdbService.getUpcomingReleases("GB", fromDateStr, toDateStr, 3)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    callback.onFilmsLoaded(result.results)
                }, { error ->
                    // TODO - Handle error
                    error.printStackTrace()
                })
    }

    fun getFavouriteFilms(callback: FilmsDataSource.LoadFilmsCallback) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd");

        val now: Calendar = Calendar.getInstance()
        val nowStr = dateFormat.format(now.time)
        now.add(Calendar.DATE, -14)
        val twoWeeksAgoStr = dateFormat.format(now.time)

        val tmdbService = TheMovieDbService.getTmdbService()
        tmdbService.getUpcomingReleases("GB", twoWeeksAgoStr, nowStr, 3)
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