package com.test.data.offline.usecase

import com.nhaarman.mockito_kotlin.whenever
import com.test.data.offline.model.CharacterName
import com.test.data.offline.repository.OfflineRepository
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetCharacterNameUseCaseTest {

    @Mock
    private lateinit var repository: OfflineRepository

    private lateinit var getCharacterNameUseCase: GetCharacterNameUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getCharacterNameUseCase = GetCharacterNameUseCase(repository)
    }

    @Test
    fun `GIVEN that user has saved character name THEN fetch character name`() {
        val name = getCharacterName()
        whenever(repository.getCharacterName()).thenReturn(Single.just(name))
        val testObserver = getCharacterNameUseCase.get().test()
        testObserver.assertValue(name)
    }

    private fun getCharacterName(first: String = "first", last: String = "last"): CharacterName {
        return CharacterName(first, last)
    }
}