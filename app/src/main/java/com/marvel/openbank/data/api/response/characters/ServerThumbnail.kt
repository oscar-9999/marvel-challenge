package com.marvel.openbank.data.api.response.characters


import com.google.gson.annotations.SerializedName

data class ServerThumbnail(
    @SerializedName("extension")
    val extension: String,
    @SerializedName("path")
    val path: String
)