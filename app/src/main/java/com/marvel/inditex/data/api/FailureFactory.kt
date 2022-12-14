package com.marvel.inditex.data.api

import com.google.gson.Gson
import com.marvel.inditex.domain.RequestFailure
import com.marvel.inditex.domain.ResultOf
import okhttp3.ResponseBody
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class FailureFactory {

    open fun handleCode(code: Int, errorBody: ResponseBody?) =
        ResultOf
            .Failure(
                requestFailure = createApiError(errorBody, code)
            )

    open fun handleException(exception: Throwable) =
        ResultOf.Failure(
            requestFailure = when (exception) {
                is UnknownHostException, is SocketTimeoutException -> RequestFailure.NoConnection
                else -> RequestFailure.ApiError()
            }
        )

    private fun createApiError(responseBody: ResponseBody?, code: Int): RequestFailure {
        try {
            val jsonError = responseBody?.charStream()?.readText()
            responseBody?.let {
                val gson = Gson()
                val error = gson.fromJson(jsonError, ErrorResponse::class.java)
                val errorMessage: String = error.message
                val errorCode: Int = error.status

                return when (code) {
                    400,401 -> RequestFailure.InvalidRequest(errorCode, errorMessage)
                    404 -> RequestFailure.NotFound(errorCode, errorMessage)
                    500 -> RequestFailure.InternalError(errorCode, errorMessage)
                    502 -> RequestFailure.BadGateway(errorCode, errorMessage)
                    else -> RequestFailure.Unknown(errorCode, errorMessage)
                }
            }
            return RequestFailure.ApiError()
        } catch (exception: Exception) {
            return RequestFailure.ApiError()
        }
    }
}