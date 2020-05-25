package com.test.data.jokes.usecase

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import com.test.data.jokes.repository.JokesRepository
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class DeleteJokeByIdLikedUseCaseTest {

    @Mock
    private lateinit var repository: JokesRepository

    private lateinit var deleteJokeByIdLikedUseCase: DeleteJokeByIdLikedUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        deleteJokeByIdLikedUseCase = DeleteJokeByIdLikedUseCase(repository)
    }

    @Test
    fun `WHEN joke liked id is passed as argument THEN complete deletion`() {
        whenever(repository.deleteJokeByLikedId(any())).thenReturn(Completable.complete())
        val testObserver =
            deleteJokeByIdLikedUseCase.complete(DeleteJokeByIdLikedUseCase.Args(112L)).test()
        testObserver.assertComplete()
    }
}