package com.test.data.jokes.usecase

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import com.test.data.jokes.repository.JokesRepository
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class DeleteJokeByIdUseCaseTest {

    @Mock
    private lateinit var repository: JokesRepository

    private lateinit var deleteJokeByIdLikedUseCase: DeleteJokeByIdUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        deleteJokeByIdLikedUseCase = DeleteJokeByIdUseCase(repository)
    }

    @Test
    fun `WHEN joke id is passed as argument THEN complete deletion`() {
        whenever(repository.deleteJokeById(any())).thenReturn(Completable.complete())
        val testObserver =
            deleteJokeByIdLikedUseCase.complete(DeleteJokeByIdUseCase.Args(112L)).test()
        testObserver.assertComplete()
    }
}