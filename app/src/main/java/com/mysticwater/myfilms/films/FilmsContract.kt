package com.mysticwater.myfilms.films

import com.mysticwater.myfilms.BasePresenter
import com.mysticwater.myfilms.BaseView
import com.mysticwater.myfilms.data.Film
import com.mysticwater.myfilms.data.source.FilmType

interface FilmsContract {

    interface View : BaseView<Presenter> {

        fun showFilms(films: List<Film>)

        fun showFilmDetailUi(filmId: Int)

        fun showLoadingUi(active: Boolean)

        fun showNoFilms()

        var isActive: Boolean

    }

    interface Presenter : BasePresenter {

        fun loadFilms(filmType: FilmType, forceUpdate: Boolean)

        fun openFilmDetail(film: Film)

    }
}