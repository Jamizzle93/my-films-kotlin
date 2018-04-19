package com.mysticwater.myfilms.data.source.local

import com.mysticwater.myfilms.data.Film
import com.mysticwater.myfilms.data.source.FilmType
import com.mysticwater.myfilms.data.source.FilmsDataSource
import com.mysticwater.myfilms.util.AppExecutors
import java.util.*

class FilmsLocalDataSource private constructor(
        val appExecutors: AppExecutors,
        val filmsDao: FilmsDao
) : FilmsDataSource {

    override fun getFilms(filmType: FilmType, callback: FilmsDataSource.LoadFilmsCallback) {
        appExecutors.diskIO.execute {
            var films: List<Film> = listOf()

            if (filmType == FilmType.NOW_SHOWING) {
                films = getNowShowingFilms()
            } else if (filmType == FilmType.UPCOMING) {
                films = getUpcomingFilms()
            } else if (filmType == FilmType.FAVOURITES) {
                films = filmsDao.getFavouriteFilms()
            }

            appExecutors.mainThread.execute {
                if (films.isEmpty()) {
                    callback.onDataNotAvailable()
                } else {
                    callback.onFilmsLoaded(films)
                }
            }
        }
    }

    fun getNowShowingFilms(): List<Film> {
        val toDateCal: Calendar = Calendar.getInstance()
        val toDate = toDateCal.timeInMillis
        toDateCal.add(Calendar.DATE, -14)
        val fromDate = toDateCal.timeInMillis

        return filmsDao.getFilms(fromDate, toDate)
    }

    fun getUpcomingFilms(): List<Film> {
        val fromDateCal: Calendar = Calendar.getInstance()
        fromDateCal.add(Calendar.DATE, 1)
        val fromDate = fromDateCal.timeInMillis
        fromDateCal.add(Calendar.DATE, 14)
        val toDate = fromDateCal.timeInMillis

        return filmsDao.getFilms(fromDate, toDate)
    }

    override fun getFilm(filmId: Int, callback: FilmsDataSource.GetFilmCallback) {
        appExecutors.diskIO.execute {
            val film = filmsDao.getFilmById(filmId)
            appExecutors.mainThread.execute {
                if (film != null) {
                    callback.onFilmLoaded(film)
                } else {
                    callback.onDataNotAvailable()
                }
            }
        }
    }

    override fun saveFilm(film: Film) {
        appExecutors.diskIO.execute {
            filmsDao.insertFilm(film)
        }
    }

    override fun updateFilm(film: Film) {
        appExecutors.diskIO.execute {
            filmsDao.updateFilm(film)
        }
    }

    override fun deleteAllFilms(filmType: FilmType) {
        appExecutors.diskIO.execute {
            if (filmType == FilmType.NOW_SHOWING) {
                deleteAllNowShowingFilms()
            } else if (filmType == FilmType.UPCOMING) {
                deleteAllUpcomingFilms()
            }
        }
    }

    fun deleteAllNowShowingFilms() {
        val toDateCal: Calendar = Calendar.getInstance()
        val toDate = toDateCal.timeInMillis
        toDateCal.add(Calendar.DATE, -14)
        val fromDate = toDateCal.timeInMillis

        return filmsDao.deleteFilms(fromDate, toDate)
    }

    fun deleteAllUpcomingFilms() {
        val fromDateCal: Calendar = Calendar.getInstance()
        fromDateCal.add(Calendar.DATE, 1)
        val fromDate = fromDateCal.timeInMillis
        fromDateCal.add(Calendar.DATE, 14)
        val toDate = fromDateCal.timeInMillis

        return filmsDao.deleteFilms(fromDate, toDate)
    }

    companion object {
        private var INSTANCE: FilmsLocalDataSource? = null

        @JvmStatic
        fun getInstance(appExecutors: AppExecutors, filmsDao: FilmsDao): FilmsLocalDataSource {
            if (INSTANCE == null) {
                synchronized(FilmsLocalDataSource::javaClass) {
                    INSTANCE = FilmsLocalDataSource(appExecutors, filmsDao)
                }
            }
            return INSTANCE!!
        }
    }

}
