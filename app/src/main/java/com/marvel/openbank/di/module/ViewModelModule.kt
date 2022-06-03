package com.marvel.openbank.di.module

import com.marvel.openbank.domain.interactors.GetCharacterDetailsInteractor
import com.marvel.openbank.domain.interactors.GetListCharactersInteractor
import com.marvel.openbank.presentation.ui.characterdetail.CharacterDetailViewModel
import com.marvel.openbank.presentation.ui.characterslist.CharactersListViewModel
import dagger.Module
import dagger.Provides


@Module(includes = [ViewModelBindingModule::class])
class ViewModelModule {

    @Provides
    fun providesCharactersListViewModel(
        getListCharactersInteractor: GetListCharactersInteractor
    ) =
        CharactersListViewModel(
            getListCharactersInteractor
        )

    @Provides
    fun providesCharacterDetailViewModel(
        getCharacterDetailsInteractor: GetCharacterDetailsInteractor
    ) =
        CharacterDetailViewModel(
            getCharacterDetailsInteractor
        )

}