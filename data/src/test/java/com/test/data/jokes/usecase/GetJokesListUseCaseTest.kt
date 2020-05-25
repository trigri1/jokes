package com.test.data.jokes.usecase

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import com.test.data.base.model.MappedList
import com.test.data.jokes.models.mapped.Joke
import com.test.data.jokes.repository.JokesRepository
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetJokesListUseCaseTest {

    @Mock
    private lateinit var repository: JokesRepository

    private lateinit var getJokesListUseCase: GetJokesListUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getJokesListUseCase = GetJokesListUseCase(repository)
    }

    @Test
    fun `GIVEN refresh = false THEN return jokes list from db or server SUCCESS`() {
        val list = mockJokesList()
        val refresh = false
        whenever(repository.getJokesList(any(), any(), any())).thenReturn(Single.just(list))
        val testObserver =
            getJokesListUseCase.get(GetJokesListUseCase.Args(refresh, "first", "last")).test()

        testObserver.assertValue(getMockedMappedList(list))
    }

    @Test
    fun `GIVEN refresh = true THEN return jokes list from db SUCCESS`() {
        val list = mockJokesList()
        val refresh = true
        whenever(repository.getJokesList(any(), any(), any())).thenReturn(Single.just(list))
        val testObserver =
            getJokesListUseCase.get(GetJokesListUseCase.Args(refresh, "first", "last")).test()

        testObserver.assertValue(getMockedMappedList(list))
    }

    @Test
    fun `GIVEN refresh = false AND db is empty THEN return jokes list from server SUCCESS`() {
        val list = mockJokesList()
        val refresh = false
        whenever(repository.getJokesList(any(), any(), any())).thenReturn(Single.just(list))
        val testObserver =
            getJokesListUseCase.get(GetJokesListUseCase.Args(refresh, "first", "last")).test()

        testObserver.assertValue(getMockedMappedList(list))
    }

    @Test
    fun `GIVEN refresh = false THEN return jokes list from server or db FAILED`() {
        val error = Throwable()
        val refresh = false
        whenever(repository.getJokesList(any(), any(), any())).thenReturn(Single.error(error))
        val testObserver =
            getJokesListUseCase.get(GetJokesListUseCase.Args(refresh, "first", "last")).test()

        testObserver.assertError(error)
    }

    @Test
    fun `GIVEN refresh = true THEN return jokes list from server FAILED`() {
        val error = Throwable()
        val refresh = true
        whenever(repository.getJokesList(any(), any(), any())).thenReturn(Single.error(error))
        val testObserver =
            getJokesListUseCase.get(GetJokesListUseCase.Args(refresh, "first", "last")).test()

        testObserver.assertError(error)
    }

    @Test
    fun `GIVEN refresh = false AND db is empty THEN return jokes list from server FAILED`() {
        val error = Throwable()
        val refresh = false
        whenever(repository.getJokesList(any(), any(), any())).thenReturn(Single.error(error))
        val testObserver =
            getJokesListUseCase.get(GetJokesListUseCase.Args(refresh, "first", "last")).test()

        testObserver.assertError(error)
    }

    private fun mockJokesList(): List<Joke> {
        return listOf(getJoke(1), getJoke(2), getJoke(3))
    }

    private fun getJoke(id: Long = 1, likedId: Long = -1): Joke {
        return Joke(id, "mocked joke", likedId)
    }

    private fun getMockedMappedList(list: List<Joke>): MappedList<Joke> {
        return MappedList(list)
    }
}