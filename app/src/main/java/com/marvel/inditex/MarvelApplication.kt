package com.marvel.inditex

import android.app.Application
import com.marvel.inditex.di.component.DaggerMarvelComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


open class MarvelApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        initializeComponentsHelper()
    }

    private fun initializeComponentsHelper() {
        DaggerMarvelComponent.builder().application(this).build().inject(this)
    }

    // this is required to setup Dagger2 for Activity
    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}
