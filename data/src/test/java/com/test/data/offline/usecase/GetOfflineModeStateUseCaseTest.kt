package com.test.data.offline.usecase

import com.nhaarman.mockito_kotlin.whenever
import com.test.data.offline.model.OfflineStateModel
import com.test.data.offline.repository.OfflineRepository
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetOfflineModeStateUseCaseTest {

    @Mock
    private lateinit var repository: OfflineRepository

    private lateinit var getOfflineModeStateUseCase: GetOfflineModeStateUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getOfflineModeStateUseCase = GetOfflineModeStateUseCase(repository)
    }

    @Test
    fun `FETCH offline mode state of the App WHEN state is false`() {
        val state = false
        val offlineState = getOfflineMode(state)
        whenever(repository.getOfflineState()).thenReturn(Single.just(state))
        val testObserver = getOfflineModeStateUseCase.get().test()
        testObserver.assertValue(offlineState)
    }

    @Test
    fun `FETCH offline mode state of the App WHEN state is true`() {
        val state = true
        val offlineState = getOfflineMode(state)
        whenever(repository.getOfflineState()).thenReturn(Single.just(state))
        val testObserver = getOfflineModeStateUseCase.get().test()
        testObserver.assertValue(offlineState)
    }

    private fun getOfflineMode(state: Boolean = false): OfflineStateModel {
        return OfflineStateModel(state)
    }
}