package com.marvel.openbank.mock

import com.marvel.openbank.data.api.response.characters.ServerCharactersResponse
import com.squareup.moshi.Moshi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader

class MockWebServer {

    private val moshi = Moshi.Builder().build()

    private val basePath = "/src/test/resources/raw/"

    fun responseListCharactersSuccess200(): Response<ServerCharactersResponse> {
        val response = moshi.adapter(ServerCharactersResponse::class.java)
            .fromJson(getResponseJson("responseListCharactersSuccess200.json"))!!
        return Response.success(response)
    }

    fun responseCharacterDetailSuccess200(): Response<ServerCharactersResponse> {
        val response = moshi.adapter(ServerCharactersResponse::class.java)
            .fromJson(getResponseJson("responseCharacterDetailSuccess200.json"))!!
        return Response.success(response)
    }


    fun responseListCharactersError401(): Response<ServerCharactersResponse> {
        val errorResponseBody =
            getResponseJson("responseError401.json").toResponseBody("application/json; charset=utf-8".toMediaTypeOrNull())
        return Response.error(401, errorResponseBody)
    }

    private fun getResponseJson(nameFile: String): String {
        return try {
            readFile(nameFile)
        } catch (e: Exception) {
            e.printStackTrace()
            "{}"
        }
    }

    @Throws(IOException::class)
    private fun readFile(path: String): String {
        var reader: BufferedReader? = null
        return try {
            val userDirPath = formatPath(System.getProperty("user.dir"))
            val fullPath = userDirPath + basePath + path
            reader = BufferedReader(InputStreamReader(FileInputStream(fullPath), "UTF-8"))
            reader.use {
                it.readText()
            }
        } catch (ignore: IOException) {
            ""
        } finally {
            reader?.close()
        }
    }

    private fun formatPath(path: String): String {
        return path
    }

}