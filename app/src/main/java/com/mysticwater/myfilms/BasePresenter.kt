package com.mysticwater.myfilms

interface BasePresenter<in V : BaseView> {

    fun attachView(view: V)

    fun detachView()
}