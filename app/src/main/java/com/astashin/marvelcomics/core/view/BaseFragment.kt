package com.astashin.marvelcomics.core.view

import androidx.fragment.app.Fragment
import com.astashin.marvelcomics.core.viewmodel.IViewModel
import javax.inject.Inject

open class BaseFragment<VM : IViewModel> : Fragment() {

    @Inject
    lateinit var viewModel: VM
}