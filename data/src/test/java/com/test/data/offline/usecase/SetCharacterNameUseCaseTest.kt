package com.test.data.offline.usecase

import com.nhaarman.mockito_kotlin.whenever
import com.test.data.offline.model.CharacterName
import com.test.data.offline.repository.OfflineRepository
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SetCharacterNameUseCaseTest {

    @Mock
    private lateinit var repository: OfflineRepository

    private lateinit var setCharacterNameUseCase: SetCharacterNameUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        setCharacterNameUseCase = SetCharacterNameUseCase(repository)
    }

    @Test
    fun `WHEN user sets name THEN save in the prefs`() {
        val first = "John"
        val last = "Doe"
        val name = CharacterName(first, last)
        whenever(repository.setCharacterName(name)).thenReturn(Completable.complete())
        val testObserver =
            setCharacterNameUseCase.complete(SetCharacterNameUseCase.Args(first, last)).test()
        testObserver.assertComplete()
    }
}