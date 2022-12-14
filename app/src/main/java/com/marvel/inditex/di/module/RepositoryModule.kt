package com.marvel.inditex.di.module

import com.marvel.inditex.data.DataRepositoryImpl
import com.marvel.inditex.data.api.APIService
import com.marvel.inditex.domain.repository.DataRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun providesDataRepository(
        apiService: APIService,
    ): DataRepository {
        return DataRepositoryImpl(apiService)
    }
}