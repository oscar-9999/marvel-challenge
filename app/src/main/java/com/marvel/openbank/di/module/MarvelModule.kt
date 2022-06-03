package com.marvel.openbank.di.module

import android.app.Application
import android.content.Context
import com.marvel.openbank.R
import com.marvel.openbank.presentation.widget.spinner.SpinnerLoading
import com.marvel.openbank.presentation.widget.spinner.SpinnerLoadingImpl
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class MarvelModule {

    companion object {
        const val nameApp = "Marvel Openbank Mobile Test"
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
