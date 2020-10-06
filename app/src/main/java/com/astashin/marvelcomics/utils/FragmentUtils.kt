package com.astashin.marvelcomics.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.astashin.marvelcomics.core.viewmodel.BaseViewModel

@Suppress("UNCHECKED_CAST")
inline fun <reified VM : BaseViewModel> Fragment.createViewModel(crossinline builder: () -> VM): VM {
    return viewModels<VM> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return builder.invoke() as T
            }
        }
    }.value
}