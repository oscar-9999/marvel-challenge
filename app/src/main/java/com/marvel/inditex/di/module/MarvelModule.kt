package com.marvel.inditex.di.module

import android.app.Application
import android.content.Context
import com.marvel.inditex.R
import com.marvel.inditex.presentation.widget.spinner.SpinnerLoading
import com.marvel.inditex.presentation.widget.spinner.SpinnerLoadingImpl
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class MarvelModule {

    companion object {
        const val nameApp = "Marvel Inditex Mobile Test"
    }

    @Provides
    @Singleton
    fun provideApplicationContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    @Named(nameApp)
    fun provideNameApp(context: Context): String {
        return context.getString(R.string.app_name)
    }

    @Provides
    fun provideSpinnerLoading(): SpinnerLoading {
        return SpinnerLoadingImpl()
    }

}
