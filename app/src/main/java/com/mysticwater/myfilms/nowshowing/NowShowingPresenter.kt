package com.mysticwater.myfilms.nowshowing

import com.mysticwater.myfilms.data.Film

class NowShowingPresenter(val view: NowShowingContract.View) : NowShowingContract.Presenter {

    init {
        view.presenter = this
    }

    override fun start() {
        loadFilms(false)
    }

    override fun loadFilms(forceUpdate: Boolean) {
        val filmsToShow = ArrayList<Film>()

        val film: Film = Film(0, "My film!")
        filmsToShow.add(film)

        view.showFilms(filmsToShow)
    }
}