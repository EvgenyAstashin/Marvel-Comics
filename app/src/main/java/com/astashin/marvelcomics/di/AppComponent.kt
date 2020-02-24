package com.astashin.marvelcomics.di

import android.content.Context
import com.astashin.marvelcomics.ui.date_picker.DatePickerFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(fragment: DatePickerFragment)
}