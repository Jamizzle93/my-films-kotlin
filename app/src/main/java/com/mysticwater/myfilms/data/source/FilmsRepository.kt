package com.mysticwater.myfilms.data.source

import com.mysticwater.myfilms.data.Film

class FilmsRepository(
        val filmsRemoteDataSource: FilmsDataSource
) : FilmsDataSource {

    var cachedFilms: LinkedHashMap<Int, Film> = LinkedHashMap()

    var cacheIsDirty = false

    override fun getNowShowingFilms(callback: FilmsDataSource.LoadFilmsCallback) {
        if (cachedFilms.isNotEmpty() && !cacheIsDirty) {
            callback.onFilmsLoaded(ArrayList(cachedFilms.values))
            return
        } else {
            getFilmsFromRemoteDataSource(callback)
        }
    }

    private fun getFilmsFromRemoteDataSource(callback: FilmsDataSource.LoadFilmsCallback) {
        filmsRemoteDataSource.getNowShowingFilms(object : FilmsDataSource.LoadFilmsCallback {
            override fun onFilmsLoaded(films: List<Film>) {
                refreshCache(films)
                // TODO
                //refreshLocalDataSource(tasks)
                callback.onFilmsLoaded(java.util.ArrayList(cachedFilms.values))
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }
        })
    }

    private fun refreshCache(films: List<Film>) {
        cachedFilms.clear()
        val sortedFilms = films.sortedWith(compareBy({it.title}))
        for (film in sortedFilms) {
            cacheAndPerform(film, {})
        }
        cacheIsDirty = false
    }

    private inline fun cacheAndPerform(film: Film, perform: (Film) -> Unit) {
        val cachedFilm = Film(film.id, film.title, film.poster_path)
        cachedFilms.put(cachedFilm.id, cachedFilm)
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
