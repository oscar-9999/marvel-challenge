package com.marvel.inditex.data.api

import com.marvel.inditex.data.api.response.characters.ServerCharactersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    companion object {
        //Headers
        const val CONTENT_TYPE = "Content-Type: application/json"
        const val ACCEPT = "Accept: application/json"
    }

    @Headers(CONTENT_TYPE, ACCEPT)
    @GET("v1/public/characters")
    suspend fun getCharactersList(
        @Query("limit") limit: Int, @Query("ts") ts: String, @Query("apikey") apikey: String,
        @Query("hash") hash: String,
    ): Response<ServerCharactersResponse>

    @Headers(CONTENT_TYPE, ACCEPT)
    @GET("v1/public/characters/{characterId}")
    suspend fun getCharacterDetails(
        @Path("characterId") characterId: Int,
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
    ): Response<ServerCharactersResponse>
}
