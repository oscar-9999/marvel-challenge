package com.marvel.inditex.data

import com.marvel.inditex.data.api.APIService
import com.marvel.inditex.data.api.FailureFactory
import com.marvel.inditex.data.mappers.toDomain
import com.marvel.inditex.domain.ResultOf
import com.marvel.inditex.domain.model.characters.ListCharactersModel
import com.marvel.inditex.domain.repository.DataRepository
import com.marvel.inditex.extensions.safeCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class DataRepositoryImpl(private val apiService: APIService) : DataRepository {


    override fun getCharacterDetails(
        characterId: Int,
        ts: String,
        apikey: String,
        hash: String,
    ): Flow<ResultOf<ListCharactersModel>> =
        flow {
            emit(
                apiService.getCharacterDetails(characterId = characterId, ts, apikey, hash)
                    .safeCall({ response -> response.toDomain() })
            )
        }.catch {
            emit(FailureFactory().handleException(it))
        }


    override fun getListCharacters(
        limit: Int,
        ts: String,
        apikey: String,
        hash: String,
    ): Flow<ResultOf<ListCharactersModel>> =
        flow {
            emit(
                apiService.getCharactersList(limit = limit,
                    ts,
                    apikey,
                    hash)
                    .safeCall({ response -> response.toDomain() })
            )
        }.catch {
            emit(FailureFactory().handleException(it))
        }
}
