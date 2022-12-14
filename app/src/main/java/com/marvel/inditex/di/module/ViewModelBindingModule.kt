package com.marvel.inditex.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marvel.inditex.presentation.base.ViewModelFactory
import com.marvel.inditex.presentation.ui.characterdetail.CharacterDetailViewModel
import com.marvel.inditex.presentation.ui.characterslist.CharactersListViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Module
abstract class ViewModelBindingModule {

    @Binds
    @IntoMap
    @ViewModelKey(CharactersListViewModel::class)
    @SuppressWarnings("unused")
    abstract fun bindCharactersListViewModel(charactersListViewModel: CharactersListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CharacterDetailViewModel::class)
    @SuppressWarnings("unused")
    abstract fun bindCharacterDetailViewModel(characterDetailViewModel: CharacterDetailViewModel): ViewModel


    @Binds
    @SuppressWarnings("unused")
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory


    @MapKey
    @MustBeDocumented
    @Target(AnnotationTarget.FUNCTION)
    @Retention(AnnotationRetention.RUNTIME)
    @SuppressWarnings("unused")
    annotation class ViewModelKey(val value: KClass<out ViewModel>)
}