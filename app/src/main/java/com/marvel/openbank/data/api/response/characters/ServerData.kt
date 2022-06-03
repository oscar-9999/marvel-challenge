package com.marvel.openbank.data.api.response.characters


import com.google.gson.annotations.SerializedName

data class ServerData(
    @SerializedName("count")
    val count: Int,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("results")
    val results: List<ServerResult>,
    @SerializedName("total")
    val total: Int
)