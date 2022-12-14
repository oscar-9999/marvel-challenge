package com.marvel.inditex.di.module

import com.marvel.inditex.presentation.ui.characterdetail.CharacterDetailFragment
import com.marvel.inditex.presentation.ui.characterslist.CharactersListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector()
    abstract fun bindCharacterDetailFragment(): CharacterDetailFragment

    @ContributesAndroidInjector()
    abstract fun bindCharactersListFragment(): CharactersListFragment

}