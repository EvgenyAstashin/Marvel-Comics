package com.astashin.marvelcomics.ui.base

import androidx.lifecycle.ViewModel

open class BaseViewModel<V : BaseView> : ViewModel() {

    var view: V? = null

    fun attachView(view: V) {
        this.view = view
    }

    fun detachView() {
        this.view = null
    }
}