package com.marvel.openbank.data.api.response.characters


import com.google.gson.annotations.SerializedName

data class ServerCharactersResponse(
    @SerializedName("attributionHTML")
    val attributionHTML: String,
    @SerializedName("attributionText")
    val attributionText: String,
    @SerializedName("code")
    val code: Int,
    @SerializedName("copyright")
    val copyright: String,
    @SerializedName("data")
    val data: ServerData,
    @SerializedName("etag")
    val etag: String,
    @SerializedName("status")
    val status: String
)