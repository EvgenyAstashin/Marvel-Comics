package com.astashin.marvelcomics.characters.di

import androidx.fragment.app.Fragment
import com.astashin.marvelcomics.characters.viewmodel.CharactersViewModel
import com.astashin.marvelcomics.characters.viewmodel.ICharactersViewModel
import com.astashin.marvelcomics.network.Api
import com.astashin.marvelcomics.utils.createViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object CharactersModule {

    @Provides
    fun provideViewModel(
        fragment: Fragment,
        api: Api
    ): ICharactersViewModel = fragment.createViewModel {
        CharactersViewModel(
            api = api
        )
    }
}