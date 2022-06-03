package com.marvel.openbank.domain.model.characters


import java.io.Serializable

data class ResultCharacterModel(
    var id: Int,
    var name: String,
    var thumbnail: ThumbnailModel,
    val urls: ListUrlsModel
) : Serializable