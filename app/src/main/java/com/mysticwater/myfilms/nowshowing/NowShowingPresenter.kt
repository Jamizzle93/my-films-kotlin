package com.mysticwater.myfilms.nowshowing

import com.mysticwater.myfilms.data.Film
import com.mysticwater.myfilms.data.source.FilmsDataSource
import com.mysticwater.myfilms.data.source.FilmsRepository

class NowShowingPresenter(val filmsRepository: FilmsRepository, val view: NowShowingContract.View)
    : NowShowingContract.Presenter {

    init {
        view.presenter = this
    }

    override fun start() {
        loadFilms(false)
    }

    override fun loadFilms(forceUpdate: Boolean) {
        filmsRepository.getNowShowingFilms(object : FilmsDataSource.LoadFilmsCallback {
            override fun onFilmsLoaded(films: List<Film>) {
                val filmsToShow = ArrayList<Film>()

                for (film in films) {
                    filmsToShow.add(film)
                }

                if (!view.isActive) {
                    return
                }

                view.showFilms(filmsToShow)
            }

            override fun onDataNotAvailable() {
                // TODO - Show error
            }

        })

    }
}