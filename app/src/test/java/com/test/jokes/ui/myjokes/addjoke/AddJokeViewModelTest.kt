package com.test.jokes.ui.myjokes.addjoke

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.test.data.jokes.usecase.AddJokeUseCase
import com.test.data.rx.TestSchedulerProvider
import com.test.jokes.ui.myjokes.addjoke.AddJokeViewModel.Navigation
import io.reactivex.Completable
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.concurrent.TimeUnit

class AddJokeViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val testScheduler = TestScheduler()
    private lateinit var testSchedulerProvider: TestSchedulerProvider

    @Mock
    private lateinit var addJokeUseCase: AddJokeUseCase

    private lateinit var viewModel: AddJokeViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        testSchedulerProvider = TestSchedulerProvider(testScheduler)

        viewModel = AddJokeViewModel(addJokeUseCase, testSchedulerProvider)
    }

    @Test
    fun `WHEN user clicks save ADD joke to local db and CLOES screen`() {
        whenever(addJokeUseCase.complete(any())).thenReturn(Completable.complete())

        val joke = "new joke"
        viewModel.onSaveJokeClicked(joke)

        verify(addJokeUseCase).complete(any())

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        assert(viewModel.navigation.value is Navigation.MyJokes)
    }

    @Test
    fun `WHEN user adds empty joke THEN show error`() {
        val mockErrorObserver: Observer<String> = mock()
        viewModel.error.observeForever(mockErrorObserver)

        whenever(addJokeUseCase.complete(any())).thenReturn(Completable.complete())

        val joke = ""
        viewModel.onSaveJokeClicked(joke)

        assert(viewModel.error.value!!.isEmpty().not())
    }

    @Test
    fun `WHEN user cancel CLOSE screen`() {
        viewModel.onCancelClicked()
        assert(viewModel.navigation.value is Navigation.MyJokes)
    }
}