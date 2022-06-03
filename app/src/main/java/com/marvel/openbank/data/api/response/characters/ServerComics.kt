package com.marvel.openbank.data.api.response.characters


import com.google.gson.annotations.SerializedName

data class ServerComics(
    @SerializedName("available")
    val available: Int,
    @SerializedName("collectionURI")
    val collectionURI: String,
    @SerializedName("items")
    val items: List<ServerItem>,
    @SerializedName("returned")
    val returned: Int
)