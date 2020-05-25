package com.test.data.jokes.usecase

import com.nhaarman.mockito_kotlin.whenever
import com.test.data.base.model.MappedList
import com.test.data.jokes.models.mapped.Joke
import com.test.data.jokes.repository.JokesRepository
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetUserJokesUseCaseTest {

    @Mock
    private lateinit var repository: JokesRepository

    private lateinit var getUserJokesUseCase: GetUserJokesUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getUserJokesUseCase = GetUserJokesUseCase(repository)
    }

    @Test
    fun `GIVEN that user has saved jokes THEN fetch user jokes`() {
        val list = mockJokesList()
        val mappedList = getMockedMappedList(list)

        whenever(repository.getUserJokes()).thenReturn(Observable.just(list))

        val testObserver = getUserJokesUseCase.get().test()

        testObserver.assertValue(mappedList)
    }

    @Test
    fun `GIVEN that user does not have saved jokes THEN throw ERROR`() {
        val error = Throwable()

        whenever(repository.getUserJokes()).thenReturn(Observable.error(error))

        val testObserver = getUserJokesUseCase.get().test()

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