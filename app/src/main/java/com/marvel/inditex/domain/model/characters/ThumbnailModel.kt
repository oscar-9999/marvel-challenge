package com.marvel.inditex.domain.model.characters


import java.io.Serializable

data class ThumbnailModel(
    var extension: String,
    var path: String
) : Serializable