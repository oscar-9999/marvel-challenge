package com.marvel.inditex.di.module

import android.app.Application
import com.marvel.inditex.BuildConfig
import com.marvel.inditex.R
import com.marvel.inditex.data.api.APIService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    @Named("socketTimeout")
    fun provideSocketTimeout(context: Application): Int {
        return context.resources.getInteger(R.integer.socketTimeout)
    }

    @Provides
    @Singleton
    @Named("connectionTimeout")
    fun provideConnectionTimeout(context: Application): Int {
        return context.resources.getInteger(R.integer.connectionTimeout)
    }


    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return interceptor
    }


    @Provides
    @Singleton
    internal fun provideOkHttpClient(
        context: Application,
        loggingInterceptor: HttpLoggingInterceptor,
        @Named("socketTimeout") socketTimeout: Int,
        @Named("connectionTimeout") connectionTimeout: Int,

        ): OkHttpClient {
        val clientBuilder = OkHttpClient().newBuilder()
        clientBuilder.readTimeout(socketTimeout.toLong(), TimeUnit.SECONDS)
        clientBuilder.connectTimeout(connectionTimeout.toLong(), TimeUnit.SECONDS)
        clientBuilder.addInterceptor(loggingInterceptor)

        return clientBuilder.build()
    }

    @Provides
    @Singleton
    internal fun provideRetrofitClient(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.HOST)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): APIService {
        return retrofit.create(APIService::class.java)
    }
}
