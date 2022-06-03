package com.marvel.openbank.data.api.response.characters


import com.google.gson.annotations.SerializedName

data class ServerItemX(
    @SerializedName("name")
    val name: String,
    @SerializedName("resourceURI")
    val resourceURI: String
)