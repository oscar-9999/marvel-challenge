package com.marvel.openbank.data.api.response.characters


import com.google.gson.annotations.SerializedName

data class ServerEvents(
    @SerializedName("available")
    val available: Int,
    @SerializedName("collectionURI")
    val collectionURI: String,
    @SerializedName("items")
    val items: List<ServerItemX>,
    @SerializedName("returned")
    val returned: Int
)