package com.marvel.openbank.di.component

import android.app.Application
import com.marvel.openbank.MarvelApplication
import com.marvel.openbank.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [AndroidInjectionModule::class, ActivityModule::class, FragmentModule::class, MarvelModule::class,
        ViewModelModule::class, NetworkModule::class, RepositoryModule::class]
)

interface MarvelComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): MarvelComponent
    }

    fun inject(app: MarvelApplication)
}
