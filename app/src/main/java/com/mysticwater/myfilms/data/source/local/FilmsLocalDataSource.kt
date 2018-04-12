package com.mysticwater.myfilms.data.source.local

import com.mysticwater.myfilms.data.Film
import com.mysticwater.myfilms.data.source.FilmType
import com.mysticwater.myfilms.data.source.FilmsDataSource
import com.mysticwater.myfilms.util.AppExecutors

class FilmsLocalDataSource private constructor(
        val appExecutors: AppExecutors,
        val filmsDao: FilmsDao
): FilmsDataSource {

    override fun getFilms(filmType: FilmType, callback: FilmsDataSource.LoadFilmsCallback) {
        appExecutors.diskIO.execute {
            var films: List<Film> = listOf()

            if (filmType == FilmType.NOW_SHOWING) {
                films = filmsDao.getNowShowingFilms()
            } else if (filmType == FilmType.UPCOMING) {
                films = filmsDao.getUpcomingFilms()
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


    override fun getFilm(filmId: Int, callback: FilmsDataSource.GetFilmCallback) {
        appExecutors.diskIO.execute{
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


}
