package com.marvel.inditex.data.mappers

import com.marvel.inditex.data.api.response.characters.ServerCharactersResponse
import com.marvel.inditex.data.api.response.characters.ServerResult
import com.marvel.inditex.data.api.response.characters.ServerThumbnail
import com.marvel.inditex.data.api.response.characters.ServerUrl
import com.marvel.inditex.domain.model.characters.*


fun ServerCharactersResponse.toDomain() = ListCharactersModel(
    results = this.data.results.map { it.toServerResultDomain() }
)

fun ServerResult.toServerResultDomain(): ResultCharacterModel {
    return ResultCharacterModel(
        id = id,
        name = name,
        description = description,
        thumbnail = thumbnail.toServerThumbnailDomain(),
        urls = urls.toServerUrlDomain()
    )
}

fun ServerThumbnail.toServerThumbnailDomain() = ThumbnailModel(extension = extension, path = path)

fun List<ServerUrl>.toServerUrlDomain() = ListUrlsModel(
    urls = this.map { it.toBDomain() }
)

fun ServerUrl.toBDomain(): UrlsModel {
    return UrlsModel(
        type = type,
        url = url
    )
}