package com.marvel.openbank.domain.repository

import com.marvel.openbank.domain.ResultOf
import com.marvel.openbank.domain.model.characters.ListCharactersModel
import kotlinx.coroutines.flow.Flow


interface DataRepository {

    fun getCharacterDetails(
        characterId: Int,
        ts: String,
        apikey: String,
        hash: String,
    ): Flow<ResultOf<ListCharactersModel>>

    fun getListCharacters(
        limit: Int,
        ts: String,
        apikey: String,
        hash: String,
    ): Flow<ResultOf<ListCharactersModel>>
}
