package com.mysticwater.myfilms.films

import com.mysticwater.myfilms.data.Film
import com.mysticwater.myfilms.data.source.FilmType
import com.mysticwater.myfilms.data.source.FilmsDataSource
import com.mysticwater.myfilms.data.source.FilmsRepository

class FilmsPresenter(val filmsRepository: FilmsRepository, val view: FilmsContract.View)
    : FilmsContract.Presenter {

    init {
        view.presenter = this
    }

    override fun start() {
    }

    override fun loadFilms(filmType: FilmType, forceUpdate: Boolean) {
        view.showLoadingUi(true)

        filmsRepository.getFilms(filmType, object : FilmsDataSource.LoadFilmsCallback {
            override fun onFilmsLoaded(films: List<Film>) {
                val filmsToShow = ArrayList<Film>()

                for (film in films) {
                    filmsToShow.add(film)
                }

                if (!view.isActive) {
                    return
                }

                view.showLoadingUi(false)
                view.showFilms(filmsToShow)
            }

            override fun onDataNotAvailable() {
                view.showLoadingUi(false)
                // TODO - Show error
            }

        })

    }

    override fun openFilmDetail(film: Film) {
        view.showFilmDetailUi(film.id)
    }
}