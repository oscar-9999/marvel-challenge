package com.marvel.openbank.data

import com.marvel.openbank.data.api.APIService
import com.marvel.openbank.domain.RequestFailure
import com.marvel.openbank.domain.ResultOf
import com.marvel.openbank.domain.model.characters.ListCharactersModel
import com.marvel.openbank.domain.model.characters.ThumbnailModel
import com.marvel.openbank.mock.RepositoryTest
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetListCharactersTest : RepositoryTest() {
    private lateinit var sut: DataRepositoryImpl

    @MockK
    private lateinit var apiService: APIService

    private val limit = 10
    private val ts = "1654250799"
    private val apikey = "20c2be39ae8a5b2634053fc27ddb1262"
    private val hash = "1445b1204407eb9b03d43b505c1ac0d6"


    @Before
    fun setup() {
        super.setUp()
        sut = DataRepositoryImpl(apiService)
    }

    @Test
    fun `on successful request object return flow with object`() = runBlocking {
        coEvery {
            apiService.getCharactersList(limit = limit,
                ts = ts,
                apikey = apikey,
                hash = hash)
        } returns mockWebServer.responseListCharactersSuccess200()

        sut.getListCharacters(limit = limit,
            ts = ts,
            apikey = apikey,
            hash = hash).collect {
            assertServiceContainsExpectedValues((it as ResultOf.Success<ListCharactersModel>).value)
        }
    }

    private fun assertServiceContainsExpectedValues(list: ListCharactersModel) {
        val thumbnail = ThumbnailModel(extension = "jpg",
            "http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784")

        Assert.assertEquals(1011334, list.results[0].id)
        Assert.assertEquals("3-D Man", list.results[0].name)
        Assert.assertEquals(thumbnail, list.results[0].thumbnail)
    }

    @Test
    fun `on api error request object return flow with api error`() = runBlocking {
        coEvery {
            apiService.getCharactersList(limit = limit,
                ts = ts,
                apikey = apikey,
                hash = hash)
        } returns mockWebServer.responseListCharactersError401()

        sut.getListCharacters(limit = limit,
            ts = ts,
            apikey = apikey,
            hash = hash).collect {
            val failure = ((it as ResultOf.Failure).requestFailure as RequestFailure.InvalidRequest)
            Assert.assertEquals("The passed API key is invalid.", failure.message)
        }
    }

}