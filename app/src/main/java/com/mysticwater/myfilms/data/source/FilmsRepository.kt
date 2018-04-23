package com.mysticwater.myfilms.data.source

import com.mysticwater.myfilms.data.Film
import com.mysticwater.myfilms.data.source.local.FilmsLocalDataSource
import java.util.*

class FilmsRepository(
        val filmsRemoteDataSource: FilmsDataSource,
        val filmsLocalDataSource: FilmsDataSource
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
        } else if (filmType == FilmType.FAVOURITES) {
            getFilmsFromLocalDataSource(filmType, callback)
        } else {
            getFilmsFromRemoteDataSource(filmType, callback)
        }
    }

    override fun getFilm(filmId: Int, callback: FilmsDataSource.GetFilmCallback) {
        filmsLocalDataSource.getFilm(filmId, object : FilmsDataSource.GetFilmCallback {
            override fun onFilmLoaded(film: Film) {
                if (film.runtime > 0) {
                    callback.onFilmLoaded(film)
                } else {
                    getFilmFromRemoteDataSource(filmId, callback)
                }
            }

            override fun onDataNotAvailable() {
                getFilmFromRemoteDataSource(filmId, callback)
            }
        })
    }

    fun getFilmFromRemoteDataSource(filmId: Int, callback: FilmsDataSource.GetFilmCallback) {
        filmsRemoteDataSource.getFilm(filmId, object : FilmsDataSource.GetFilmCallback {
            override fun onFilmLoaded(film: Film) {
                filmsLocalDataSource.updateFilm(film)
                callback.onFilmLoaded(film)
            }

            override fun onDataNotAvailable() {
                // Handle error
            }
        })
    }

    override fun saveFilm(film: Film) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateFilm(film: Film) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteAllFilms(filmType: FilmType) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    public fun favouriteFilm(film: Film) {
        film.favourite = !film.favourite
        filmsLocalDataSource.updateFilm(film)
    }

    private fun getFilmsFromRemoteDataSource(filmType: FilmType, callback: FilmsDataSource.LoadFilmsCallback) {
        filmsRemoteDataSource.getFilms(filmType, object : FilmsDataSource.LoadFilmsCallback {
            override fun onFilmsLoaded(films: List<Film>) {
                refreshCache(filmType, films)
                refreshLocalDataSource(filmType, films)

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

    private fun getFilmsFromLocalDataSource(filmType: FilmType, callback: FilmsDataSource.LoadFilmsCallback) {
        filmsLocalDataSource.getFilms(filmType, object : FilmsDataSource.LoadFilmsCallback {
            override fun onFilmsLoaded(films: List<Film>) {
                refreshCache(filmType, films)

                if (filmType == FilmType.FAVOURITES) {
                    callback.onFilmsLoaded(films)
                    return
                }

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
            if (film.backdrop_path != null) {
                cacheAndPerform(filmType, film, {})
            }
        }
        cacheIsDirty = false
    }

    private fun refreshLocalDataSource(filmType: FilmType, films: List<Film>) {
        filmsLocalDataSource.deleteAllFilms(filmType)
        for (film in films) {
            filmsLocalDataSource.saveFilm(film)
        }
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
                film.tagline,
                film.vote_average,
                film.vote_count
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
        fun getInstance(filmsRemoteDataSource: FilmsDataSource, filmsLocalDataSource: FilmsDataSource): FilmsRepository {
            if (needNewInstance) {
                INSTANCE = FilmsRepository(filmsRemoteDataSource, filmsLocalDataSource)
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
