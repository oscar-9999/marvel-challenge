package com.marvel.openbank.di.module

import com.marvel.openbank.presentation.ui.characterdetail.CharacterDetailFragment
import com.marvel.openbank.presentation.ui.characterslist.CharactersListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector()
    abstract fun bindCharacterDetailFragment(): CharacterDetailFragment

    @ContributesAndroidInjector()
    abstract fun bindCharactersListFragment(): CharactersListFragment

}