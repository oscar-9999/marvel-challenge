package com.marvel.inditex.domain

import java.io.Serializable

sealed class RequestFailure(val statusCode: Int, var message: String) : Serializable {
    class InvalidRequest(statusCode: Int, message: String) : RequestFailure(statusCode, message)
    class NotFound(statusCode: Int, message: String) : RequestFailure(statusCode, message)
    class InternalError(statusCode: Int, message: String) : RequestFailure(statusCode, message)
    class BadGateway(statusCode: Int, message: String) : RequestFailure(statusCode, message)
    class Unknown(statusCode: Int, message: String) : RequestFailure(statusCode, message)
    class ApiError : RequestFailure(-1, "")
    object NoConnection : RequestFailure(-1, "")
}