package com.astashin.marvelcomics.ui.comics

import com.astashin.marvelcomics.ui.base.BaseView

interface ComicsListView : BaseView {

    fun showError(error: String? = null)
}