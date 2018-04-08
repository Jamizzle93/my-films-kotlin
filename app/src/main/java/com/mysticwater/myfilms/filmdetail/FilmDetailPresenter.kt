package com.mysticwater.myfilms.filmdetail

import com.mysticwater.myfilms.data.Film
import com.mysticwater.myfilms.data.source.FilmsDataSource
import com.mysticwater.myfilms.data.source.FilmsRepository

class FilmDetailPresenter(
        private val filmId: Int,
        private val filmsRepository: FilmsRepository,
        private val filmDetailView: FilmDetailContract.View
) : FilmDetailContract.Presenter {

    init {
        filmDetailView.presenter = this
    }

    override fun start() {
        getFilm()
    }

    override fun getFilm() {
        if (filmId <= 0) {
            filmDetailView.showNoFilm()
            return
        }

        filmDetailView.setLoadingIndicator(true)
        filmsRepository.getFilm(filmId, object : FilmsDataSource.GetFilmCallback {
            override fun onFilmLoaded(film: Film) {
                with(filmDetailView) {
                    if (!isActive) {
                        return@onFilmLoaded
                    }

                    showFilm(film)
                }
            }

            override fun onDataNotAvailable() {
                with(filmDetailView) {
                    if (!isActive) {
                        return@onDataNotAvailable
                    }
                    showNoFilm()
                }
            }
        })
    }

}
