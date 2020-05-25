package com.test.data.offline.usecase

import com.nhaarman.mockito_kotlin.whenever
import com.test.data.offline.repository.OfflineRepository
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SetOfflineModeStateUseCaseTest {

    @Mock
    private lateinit var repository: OfflineRepository

    private lateinit var setOfflineModeStateUseCase: SetOfflineModeStateUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        setOfflineModeStateUseCase = SetOfflineModeStateUseCase(repository)
    }

    @Test
    fun `WHEN user switch on offline mode THEN save true in prefs`() {
        val state = true
        whenever(repository.setOfflineState(state)).thenReturn(Completable.complete())
        val testObserver =
            setOfflineModeStateUseCase.complete(SetOfflineModeStateUseCase.Args(state)).test()
        testObserver.assertComplete()
    }

    @Test
    fun `WHEN user switch off offline mode THEN save true in prefs`() {
        val state = false
        whenever(repository.setOfflineState(state)).thenReturn(Completable.complete())
        val testObserver =
            setOfflineModeStateUseCase.complete(SetOfflineModeStateUseCase.Args(state)).test()
        testObserver.assertComplete()
    }
}