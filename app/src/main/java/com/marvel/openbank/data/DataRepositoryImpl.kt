package com.marvel.openbank.data

import com.marvel.openbank.data.api.APIService
import com.marvel.openbank.data.api.FailureFactory
import com.marvel.openbank.data.mappers.toDomain
import com.marvel.openbank.domain.ResultOf
import com.marvel.openbank.domain.model.characters.ListCharactersModel
import com.marvel.openbank.domain.repository.DataRepository
import com.marvel.openbank.extensions.safeCall
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
                apiService.getCharacterDescription(characterId = characterId, ts, apikey, hash)
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
