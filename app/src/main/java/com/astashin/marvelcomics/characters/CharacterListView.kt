package com.astashin.marvelcomics.characters

import com.astashin.marvelcomics.ui.base.BaseView

interface CharacterListView : BaseView {

    fun showError(error: String? = null)
}