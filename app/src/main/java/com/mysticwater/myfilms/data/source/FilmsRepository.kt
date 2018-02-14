package com.mysticwater.myfilms.data.source

import com.mysticwater.myfilms.data.Film
import java.util.*

class FilmsRepository(
        val filmsRemoteDataSource: FilmsDataSource
) : FilmsDataSource {

    var cachedNowShowingFilms: LinkedHashMap<Int, Film> = LinkedHashMap()
    var cachedUpcomingFilms: LinkedHashMap<Int, Film> = LinkedHashMap()

    var cacheIsDirty = false

    override fun getFilms(filmType: FilmType, callback: FilmsDataSource.LoadFilmsCallback) {

        var cachedFilms: LinkedHashMap<Int, Film> = LinkedHashMap()
        if (filmType == FilmType.NOW_SHOWING) {
            cachedFilms = cachedNowShowingFilms
        } else if (filmType == FilmType.UPCOMING) {
            cachedFilms = cachedUpcomingFilms
        }

        if (cachedFilms.isNotEmpty() && !cacheIsDirty) {
            callback.onFilmsLoaded(ArrayList(cachedFilms.values))
            return
        } else {
            getFilmsFromRemoteDataSource(filmType, callback)
        }
    }

    override fun getFilm(filmId: Int, callback: FilmsDataSource.GetFilmCallback) {
        filmsRemoteDataSource.getFilm(filmId, object : FilmsDataSource.GetFilmCallback {
            override fun onFilmLoaded(film: Film) {
                callback.onFilmLoaded(film)
//                cacheAndPerform(film) {
//                    callback.onFilmLoaded(it)
//                }
            }

            override fun onDataNotAvailable() {
                // Handle error
            }
        })
    }

    private fun getFilmsFromRemoteDataSource(filmType: FilmType, callback: FilmsDataSource.LoadFilmsCallback) {
        filmsRemoteDataSource.getFilms(filmType, object : FilmsDataSource.LoadFilmsCallback {
            override fun onFilmsLoaded(films: List<Film>) {
                refreshCache(filmType, films)
                // TODO
                //refreshLocalDataSource(tasks)

                var cachedFilms: LinkedHashMap<Int, Film> = LinkedHashMap()
                if (filmType == FilmType.NOW_SHOWING) {
                    cachedFilms = cachedNowShowingFilms
                } else if (filmType == FilmType.UPCOMING) {
                    cachedFilms = cachedUpcomingFilms
                }

                callback.onFilmsLoaded(java.util.ArrayList(cachedFilms.values))
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }
        })
    }

    private fun refreshCache(filmType: FilmType, films: List<Film>) {
        if (filmType == FilmType.UPCOMING) {
            cachedUpcomingFilms.clear()
        } else if (filmType == FilmType.NOW_SHOWING) {
            cachedNowShowingFilms.clear()
        }

        val sortedFilms = films.sortedWith(compareBy({ it.release_date }))
        for (film in sortedFilms) {
            cacheAndPerform(filmType, film, {})
        }
        cacheIsDirty = false
    }

    private inline fun cacheAndPerform(filmType: FilmType, film: Film, perform: (Film) -> Unit) {
        val cachedFilm = Film(
                film.id,
                film.title,
                film.poster_path,
                film.release_date,
                film.runtime,
                film.overview,
                film.backdrop_path,
                film.imdb_id,
                film.tagline
        )
        if (filmType == FilmType.UPCOMING) {
            cachedUpcomingFilms.put(cachedFilm.id, cachedFilm)
        } else if (filmType == FilmType.NOW_SHOWING) {
            cachedNowShowingFilms.put(cachedFilm.id, cachedFilm)
        }
        perform(cachedFilm)
    }

    companion object {

        private lateinit var INSTANCE: FilmsRepository
        private var needNewInstance = true
        /**
         * Returns the single instance of this class, creating it if necessary.

         * @param tasksRemoteDataSource the backend data source
         * *
         * @param tasksLocalDataSource  the device storage data source
         * *
         * @return the [TasksRepository] instance
         */
        @JvmStatic
        fun getInstance(filmsRemoteDataSource: FilmsDataSource): FilmsRepository {
            if (needNewInstance) {
                INSTANCE = FilmsRepository(filmsRemoteDataSource)
                needNewInstance = false
            }
            return INSTANCE
        }

        /**
         * Used to force [getInstance] to create a new instance
         * next time it's called.
         */
        @JvmStatic
        fun destroyInstance() {
            needNewInstance = true
        }
    }

}
