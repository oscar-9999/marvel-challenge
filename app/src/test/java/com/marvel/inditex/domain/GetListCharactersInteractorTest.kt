package com.marvel.inditex.domain

import com.marvel.inditex.domain.interactors.GetListCharactersInteractor
import com.marvel.inditex.domain.model.characters.*
import com.marvel.inditex.domain.repository.DataRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetListCharactersInteractorTest {

    private lateinit var sut: GetListCharactersInteractor

    @MockK
    private lateinit var repository: DataRepository

    companion object {
        const val limit = 10
        const val ts = "1654526225"
        const val apikey = "20c2be39ae8a5b2634053fc27ddb1262"
        const val hash = "b122d830e354b894447e901e4238561b"
    }

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        sut = GetListCharactersInteractor(repository)
    }

    @Test
    fun `on execute call repository only once`() {
        mockRepositoryReponse()

        sut.setParams(limit, ts, apikey, hash)
        sut.execute()


        verify(exactly = 1) { repository.getListCharacters(limit, ts, apikey, hash) }
    }

    @Test
    fun `on receive data from repository return it`() = runBlocking {
        mockRepositoryReponse()

        sut.setParams(limit, ts, apikey, hash)
        sut.execute().collect {
            Assert.assertNotNull((it as ResultOf.Success).value)
        }
    }

    private fun mockRepositoryReponse() {
        val mockThumbnailModel = ThumbnailModel("", "")
        val mockUrlsModel = UrlsModel("", "")
        val mockListUrls = ListUrlsModel(urls = listOf(mockUrlsModel))
        val mockResultCharacterModel = ResultCharacterModel(1011334, "3-D Man", "",
            mockThumbnailModel, mockListUrls)
        val mockListCharactersModel =
            ListCharactersModel(results = listOf(mockResultCharacterModel))

        coEvery {
            repository.getListCharacters(any(), any(), any(), any())
        } returns flow {
            emit(ResultOf.Success(mockListCharactersModel))
        }
    }
}