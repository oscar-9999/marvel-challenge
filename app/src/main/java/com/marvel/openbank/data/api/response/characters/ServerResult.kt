package com.marvel.openbank.data.api.response.characters


import com.google.gson.annotations.SerializedName

data class ServerResult(
    @SerializedName("comics")
    val comics: ServerComics,
    @SerializedName("description")
    val description: String,
    @SerializedName("events")
    val events: ServerEvents,
    @SerializedName("id")
    val id: Int,
    @SerializedName("modified")
    val modified: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("resourceURI")
    val resourceURI: String,
    @SerializedName("series")
    val series: ServerSeries,
    @SerializedName("stories")
    val stories: ServerStories,
    @SerializedName("thumbnail")
    val thumbnail: ServerThumbnail,
    @SerializedName("urls")
    val urls: List<ServerUrl>
)