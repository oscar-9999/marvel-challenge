package com.marvel.inditex.domain.interactors

import com.marvel.inditex.domain.repository.DataRepository
import javax.inject.Inject

class GetCharacterDetailsInteractor
@Inject constructor(private val repository: DataRepository)  {

    private var characterId: Int = 0
    private lateinit var ts: String
    private lateinit var apikey: String
    private lateinit var hash: String

    fun setParams(characterId: Int, ts: String, apikey: String, hash: String) {
        this.characterId = characterId
        this.ts = ts
        this.apikey = apikey
        this.hash = hash
    }

    fun execute() = repository.getCharacterDetails(characterId, ts, apikey, hash)

}