package com.test.data.jokes.usecase

import com.nhaarman.mockito_kotlin.whenever
import com.test.data.base.model.MappedList
import com.test.data.jokes.models.mapped.Joke
import com.test.data.jokes.repository.JokesRepository
import com.test.data.offline.model.CharacterName
import com.test.data.offline.usecase.GetCharacterNameUseCase
import com.test.data.rx.SchedulerProvider
import com.test.data.rx.TrampolineSchedulerProvider
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetRandomJokeUseCaseTest {

    @Mock
    private lateinit var repository: JokesRepository

    @Mock
    private lateinit var getCharacterNameUseCase: GetCharacterNameUseCase

    private lateinit var schedulerProvider: SchedulerProvider
    private lateinit var getRandomJokeUseCase: GetRandomJokeUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        schedulerProvider = TrampolineSchedulerProvider()
        getRandomJokeUseCase = GetRandomJokeUseCase(repository, getCharacterNameUseCase)
    }

    @Test
    fun `GIVEN user has saved jokes THEN return random joke db`() {
        val name = getCharacterName()
        val list = mockJokesList()
        val mappedList = getMockedMappedList(list)

        whenever(getCharacterNameUseCase.get()).thenReturn(Single.just(name))
        whenever(repository.getRandomJoke("first", "last")).thenReturn(Single.just(list))

        val testObserver = getRandomJokeUseCase.get().test()

        testObserver.assertValue(mappedList)
    }

    @Test
    fun `GIVEN user does not have saved jokes THEN throw ERROR`() {
        val name = getCharacterName()
        val error = Throwable()

        whenever(getCharacterNameUseCase.get()).thenReturn(Single.just(name))
        whenever(repository.getRandomJoke("first", "last")).thenReturn(Single.error(error))

        val testObserver = getRandomJokeUseCase.get().test()

        testObserver.assertError(error)
    }

    @Test
    fun `GIVEN user has saved jokes AND a character name is available THEN return random joke with replaced name`() {
        val first = "John"
        val last = "Doe"
        val name = getCharacterName(first, last)
        val list = listOf(Joke(1, "mocked joke John Doe", 12))
        val mappedList = MappedList(list)

        whenever(getCharacterNameUseCase.get()).thenReturn(Single.just(name))
        whenever(repository.getRandomJoke(first, last)).thenReturn(Single.just(list))

        val testObserver = getRandomJokeUseCase.get().test()

        testObserver.assertValue(mappedList)
    }

    private fun getCharacterName(first: String = "first", last: String = "last"): CharacterName {
        return CharacterName(first, last)
    }

    private fun mockJokesList(): List<Joke> {
        return listOf(getJoke(1))
    }

    private fun getJoke(id: Long = 1, likedId: Long = -1): Joke {
        return Joke(id, "mocked joke Chuck Norris", likedId)
    }

    private fun getMockedMappedList(list: List<Joke>): MappedList<Joke> {
        return MappedList(list)
    }
}