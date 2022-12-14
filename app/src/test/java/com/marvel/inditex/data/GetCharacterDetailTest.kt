package com.marvel.inditex.data

import com.marvel.inditex.data.api.APIService
import com.marvel.inditex.domain.RequestFailure
import com.marvel.inditex.domain.ResultOf
import com.marvel.inditex.domain.model.characters.ListCharactersModel
import com.marvel.inditex.domain.model.characters.ThumbnailModel
import com.marvel.inditex.mock.RepositoryTest
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetCharacterDetailTest : RepositoryTest() {
    private lateinit var sut: DataRepositoryImpl

    @MockK
    private lateinit var apiService: APIService

    private val characterId = 1011334
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
            apiService.getCharacterDetails(characterId = characterId,
                ts = ts,
                apikey = apikey,
                hash = hash)
        } returns mockWebServer.responseListCharactersSuccess200()

        sut.getCharacterDetails(characterId = characterId,
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
            apiService.getCharacterDetails(characterId = characterId,
                ts = ts,
                apikey = apikey,
                hash = hash)
        } returns mockWebServer.responseListCharactersError401()

        sut.getCharacterDetails(characterId = characterId,
            ts = ts,
            apikey = apikey,
            hash = hash).collect {
            val failure = ((it as ResultOf.Failure).requestFailure as RequestFailure.InvalidRequest)
            Assert.assertEquals("The passed API key is invalid.", failure.message)
        }
    }

}