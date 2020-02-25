package com.astashin.marvelcomics.ui.characters

import com.astashin.marvelcomics.ui.base.BaseView

interface CharacterListView : BaseView {

    fun showError(error: String? = null)
}