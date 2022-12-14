package com.marvel.inditex.di.module

import com.marvel.inditex.domain.interactors.GetCharacterDetailsInteractor
import com.marvel.inditex.domain.interactors.GetListCharactersInteractor
import com.marvel.inditex.presentation.ui.characterdetail.CharacterDetailViewModel
import com.marvel.inditex.presentation.ui.characterslist.CharactersListViewModel
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