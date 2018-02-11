package com.mysticwater.myfilms.filmdetail

import com.mysticwater.myfilms.BasePresenter
import com.mysticwater.myfilms.BaseView
import com.mysticwater.myfilms.data.Film

interface FilmDetailContract {

    interface View : BaseView<Presenter> {

        val isActive: Boolean

        fun setLoadingIndicator(active: Boolean)

        fun showFilm(film: Film)

        fun showNoFilm()
    }

    interface Presenter : BasePresenter {

        fun getFilm()
    }

}