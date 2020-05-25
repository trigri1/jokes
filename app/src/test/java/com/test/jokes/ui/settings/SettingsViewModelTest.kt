package com.test.jokes.ui.settings

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.test.data.offline.model.CharacterName
import com.test.data.offline.model.OfflineStateModel
import com.test.data.offline.usecase.GetCharacterNameUseCase
import com.test.data.offline.usecase.GetOfflineModeStateUseCase
import com.test.data.offline.usecase.SetCharacterNameUseCase
import com.test.data.offline.usecase.SetOfflineModeStateUseCase
import com.test.data.rx.TestSchedulerProvider
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.concurrent.TimeUnit

class SettingsViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val testScheduler = TestScheduler()
    private lateinit var testSchedulerProvider: TestSchedulerProvider

    @Mock
    private lateinit var getOfflineModeStateUseCase: GetOfflineModeStateUseCase

    @Mock
    private lateinit var setOfflineModeStateUseCase: SetOfflineModeStateUseCase

    @Mock
    private lateinit var getCharacterNameUseCase: GetCharacterNameUseCase

    @Mock
    private lateinit var setCharacterNameUseCase: SetCharacterNameUseCase

    private lateinit var viewModel: SettingsViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        testSchedulerProvider = TestSchedulerProvider(testScheduler)
    }

    @Test
    fun `WHEN screen starts THEN get offline state and character name`() {
        val nameObserver: Observer<CharacterName> = mock()
        val offlineStateObserver: Observer<Boolean> = mock()

        val offlineState = getOfflineMode()
        val name = getCharacterName()

        mockResponses(offlineState, name)

        viewModel = SettingsViewModel(
            getOfflineModeStateUseCase,
            setOfflineModeStateUseCase,
            getCharacterNameUseCase,
            setCharacterNameUseCase,
            testSchedulerProvider
        )

        viewModel.characterName.observeForever(nameObserver)
        viewModel.offlineMode.observeForever(offlineStateObserver)

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)

        verify(offlineStateObserver).onChanged(offlineState.useOffline)
        verify(nameObserver).onChanged(name)

        assert(viewModel.offlineMode.value == offlineState.useOffline)
        assert(viewModel.characterName.value == name)
    }


    @Test
    fun `WHEN user clicks offline switch UPDATE offline state value`() {
        val offlineState = getOfflineMode()
        val name = getCharacterName()

        mockResponses(offlineState, name)

        whenever(setOfflineModeStateUseCase.complete(any())).thenReturn(Completable.complete())

        viewModel = SettingsViewModel(
            getOfflineModeStateUseCase,
            setOfflineModeStateUseCase,
            getCharacterNameUseCase,
            setCharacterNameUseCase,
            testSchedulerProvider
        )

        viewModel.onOfflineModeChanged(any())

        verify(setOfflineModeStateUseCase).complete(any())
    }

    @Test
    fun `WHEN user shakes device UPDATE offline state value`() {
        val offlineState = getOfflineMode()
        val name = getCharacterName()

        mockResponses(offlineState, name)

        whenever(setOfflineModeStateUseCase.complete(any())).thenReturn(Completable.complete())

        viewModel = SettingsViewModel(
            getOfflineModeStateUseCase,
            setOfflineModeStateUseCase,
            getCharacterNameUseCase,
            setCharacterNameUseCase,
            testSchedulerProvider
        )

        viewModel.onDeviceShake(any())

        verify(setOfflineModeStateUseCase).complete(any())
    }

    @Test
    fun `WHEN user leaves settings UPDATE name`() {
        val offlineState = getOfflineMode()
        val name = getCharacterName()

        mockResponses(offlineState, name)

        whenever(setCharacterNameUseCase.complete(any())).thenReturn(Completable.complete())

        viewModel = SettingsViewModel(
            getOfflineModeStateUseCase,
            setOfflineModeStateUseCase,
            getCharacterNameUseCase,
            setCharacterNameUseCase,
            testSchedulerProvider
        )

        viewModel.setCharacterName("first", "last")

        verify(setCharacterNameUseCase).complete(any())
    }

    private fun getCharacterName(): CharacterName {
        return CharacterName("John", "Doe")
    }

    private fun getOfflineMode(state: Boolean = false): OfflineStateModel {
        return OfflineStateModel(state)
    }

    private fun mockResponses(offlineState: OfflineStateModel, name: CharacterName) {
        whenever(getOfflineModeStateUseCase.get()).thenReturn(Single.just(offlineState))
        whenever(getCharacterNameUseCase.get()).thenReturn(Single.just(name))
    }
}