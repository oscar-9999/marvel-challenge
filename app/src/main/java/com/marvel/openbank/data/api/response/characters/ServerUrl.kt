package com.marvel.openbank.data.api.response.characters


import com.google.gson.annotations.SerializedName

data class ServerUrl(
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String
)