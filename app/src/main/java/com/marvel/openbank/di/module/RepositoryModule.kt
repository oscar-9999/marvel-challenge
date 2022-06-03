package com.marvel.openbank.di.module

import com.marvel.openbank.data.DataRepositoryImpl
import com.marvel.openbank.data.api.APIService
import com.marvel.openbank.domain.repository.DataRepository
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