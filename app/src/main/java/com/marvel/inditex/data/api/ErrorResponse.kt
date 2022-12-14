package com.marvel.inditex.data.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorResponse(
    @field:Json(name = "status")
    var status: Int,
    @field:Json(name = "message")
    var message: String
)