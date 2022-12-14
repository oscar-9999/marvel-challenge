package com.marvel.inditex.domain.interactors


import com.marvel.inditex.domain.repository.DataRepository
import javax.inject.Inject

class GetListCharactersInteractor
@Inject constructor(private val repository: DataRepository) {

    private var limit: Int = 0
    private lateinit var ts: String
    private lateinit var apikey: String
    private lateinit var hash: String

    fun setParams(limit: Int, ts: String, apikey: String, hash: String) {
        this.limit = limit
        this.ts = ts
        this.apikey = apikey
        this.hash = hash
    }

    fun execute() = repository.getListCharacters(limit, ts, apikey, hash)

}