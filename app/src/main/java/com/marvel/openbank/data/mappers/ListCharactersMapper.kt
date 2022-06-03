package com.marvel.openbank.data.mappers

import com.marvel.openbank.data.api.response.characters.ServerCharactersResponse
import com.marvel.openbank.domain.model.characters.*
import com.marvel.openbank.data.api.response.characters.ServerResult
import com.marvel.openbank.data.api.response.characters.ServerThumbnail
import com.marvel.openbank.data.api.response.characters.ServerUrl


fun ServerCharactersResponse.toDomain() = ListCharactersModel(
    results = this.data.results.map { it.toServerResultDomain() }
)

fun ServerResult.toServerResultDomain(): ResultCharacterModel {
    return ResultCharacterModel(
        id = id,
        name = name,
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