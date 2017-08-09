package com.mysticwater.myfilms.nowshowing

import com.mysticwater.myfilms.BasePresenter
import com.mysticwater.myfilms.BaseView
import com.mysticwater.myfilms.data.Film

interface NowShowingContract {

    interface View: BaseView<Presenter> {

        fun showFilms(films: List<Film>)

        var isActive: Boolean

    }

    interface Presenter : BasePresenter {

        fun loadFilms(forceUpdate: Boolean)

    }
}