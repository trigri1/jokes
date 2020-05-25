package com.test.data.jokes.usecase

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import com.nhaarman.mockito_kotlin.whenever
import com.test.data.base.model.MappedList
import com.test.data.jokes.models.mapped.Joke
import com.test.data.offline.model.OfflineStateModel
import com.test.data.offline.usecase.GetOfflineModeStateUseCase
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetJokesUseCaseTest {

    @Mock
    private lateinit var getJokesListUseCase: GetJokesListUseCase

    @Mock
    private lateinit var getRandomJokeUseCase: GetRandomJokeUseCase

    @Mock
    private lateinit var getOfflineModeStateUseCase: GetOfflineModeStateUseCase

    lateinit var getJokesUseCase: GetJokesUseCase


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getJokesUseCase = GetJokesUseCase(
            getJokesListUseCase,
            getRandomJokeUseCase,
            getOfflineModeStateUseCase
        )
    }

    @Test
    fun `GIVEN offline state = false THEN return jokes list from db or server SUCCESS`() {
        val list = mockJokesList()
        val mappedList = getMockedMappedList(list)
        val randomMappedList = getRandomMockedMappedList()
        val offlineState = getOfflineMode()

        val refresh = false

        whenever(getJokesListUseCase.get(any())).thenReturn(Single.just(mappedList))
        whenever(getRandomJokeUseCase.get()).thenReturn(Single.just(randomMappedList))
        whenever(getOfflineModeStateUseCase.get()).thenReturn(Single.just(offlineState))

        val testObserver =
            getJokesUseCase.get(GetJokesUseCase.Args(refresh, "first", "last")).test()

        verify(getJokesListUseCase).get(any())
        verifyZeroInteractions(getRandomJokeUseCase)
        verify(getOfflineModeStateUseCase).get()
        testObserver.assertValue(mappedList)
    }

    @Test
    fun `GIVEN offline state = false THEN return jokes list from db or server FAILED`() {
        val error = Throwable()
        val randomMappedList = getRandomMockedMappedList()
        val offlineState = getOfflineMode()

        val refresh = false

        whenever(getJokesListUseCase.get(any())).thenReturn(Single.error(error))
        whenever(getRandomJokeUseCase.get()).thenReturn(Single.just(randomMappedList))
        whenever(getOfflineModeStateUseCase.get()).thenReturn(Single.just(offlineState))

        val testObserver =
            getJokesUseCase.get(GetJokesUseCase.Args(refresh, "first", "last")).test()

        verify(getJokesListUseCase).get(any())
        verify(getOfflineModeStateUseCase).get()
        verifyZeroInteractions(getRandomJokeUseCase)
        testObserver.assertError(error)
    }

    @Test
    fun `GIVEN offline state = true THEN return jokes list from db or server SUCCESS`() {
        val list = mockJokesList()
        val mappedList = getMockedMappedList(list)
        val randomMappedList = getRandomMockedMappedList()
        val offlineState = getOfflineMode(true)

        val refresh = false

        whenever(getJokesListUseCase.get(any())).thenReturn(Single.just(mappedList))
        whenever(getRandomJokeUseCase.get()).thenReturn(Single.just(randomMappedList))
        whenever(getOfflineModeStateUseCase.get()).thenReturn(Single.just(offlineState))

        val testObserver =
            getJokesUseCase.get(GetJokesUseCase.Args(refresh, "first", "last")).test()

        verifyZeroInteractions(getJokesListUseCase)
        verify(getRandomJokeUseCase).get()
        verify(getOfflineModeStateUseCase).get()
        testObserver.assertValue(randomMappedList)
    }

    @Test
    fun `GIVEN offline state = true THEN return jokes list from db or server FAILED`() {
        val error = Throwable()
        val list = mockJokesList()
        val mappedList = getMockedMappedList(list)
        val offlineState = getOfflineMode(true)

        val refresh = false

        whenever(getJokesListUseCase.get(any())).thenReturn(Single.just(mappedList))
        whenever(getRandomJokeUseCase.get()).thenReturn(Single.error(error))
        whenever(getOfflineModeStateUseCase.get()).thenReturn(Single.just(offlineState))

        val testObserver =
            getJokesUseCase.get(GetJokesUseCase.Args(refresh, "first", "last")).test()

        verifyZeroInteractions(getJokesListUseCase)
        verify(getRandomJokeUseCase).get()
        verify(getOfflineModeStateUseCase).get()
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

    private fun getRandomMockedMappedList(): MappedList<Joke> {
        return MappedList(listOf(getJoke(12)))
    }

    private fun getOfflineMode(state: Boolean = false): OfflineStateModel {
        return OfflineStateModel(state)
    }
}