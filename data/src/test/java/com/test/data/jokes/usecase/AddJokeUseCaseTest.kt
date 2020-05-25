package com.test.data.jokes.usecase

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import com.test.data.jokes.repository.JokesRepository
import com.test.data.local.db.entities.UserJokeEntity
import com.test.data.rx.SchedulerProvider
import com.test.data.rx.TrampolineSchedulerProvider
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class AddJokeUseCaseTest {

    @Mock
    private lateinit var repository: JokesRepository

    private lateinit var schedulerProvider: SchedulerProvider
    private lateinit var addJokeUseCase: AddJokeUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        schedulerProvider = TrampolineSchedulerProvider()
        addJokeUseCase = AddJokeUseCase(repository)
    }

    @Test
    fun `WHEN joke is passed as argument THEN add joke to db`() {
        val joke = getJokeEntity(likedId = 1)
        whenever(repository.addJoke(any())).thenReturn(Completable.complete())
        val testObserver =
            addJokeUseCase.complete(AddJokeUseCase.Args(joke.joke.orEmpty(), joke.likedId)).test()
        testObserver.assertComplete()
    }

    private fun getJokeEntity(id: Int = 1, likedId: Long = -1): UserJokeEntity {
        return UserJokeEntity(id, likedId, "mocked joke entity")
    }
}