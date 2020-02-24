package com.astashin.marvelcomics

import android.app.Application
import androidx.fragment.app.Fragment
import com.astashin.marvelcomics.di.AppComponent
import com.astashin.marvelcomics.di.DaggerAppComponent

class App : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}

fun Fragment.app(): App = activity?.application as App