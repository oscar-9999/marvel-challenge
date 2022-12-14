package com.marvel.inditex.domain.model.characters


import java.io.Serializable

data class UrlsModel(
    var type: String,
    var url: String
) : Serializable