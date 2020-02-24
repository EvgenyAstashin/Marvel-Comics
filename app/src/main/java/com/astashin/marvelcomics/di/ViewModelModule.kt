package com.astashin.marvelcomics.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.astashin.marvelcomics.ViewModelFactory
import com.astashin.marvelcomics.ViewModelKey
import com.astashin.marvelcomics.ui.comics.ComicsViewModel
import com.astashin.marvelcomics.ui.date_picker.DatePickerViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(DatePickerViewModel::class)
    internal abstract fun datePickerViewModel(viewModel: DatePickerViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ComicsViewModel::class)
    internal abstract fun comicsViewModel(viewModel: ComicsViewModel): ViewModel
}