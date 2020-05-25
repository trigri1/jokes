package com.test.jokes.ui.myjokes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.test.data.base.model.MappedList
import com.test.data.jokes.models.mapped.Joke
import com.test.data.jokes.usecase.DeleteJokeByIdUseCase
import com.test.data.jokes.usecase.GetUserJokesUseCase
import com.test.data.rx.TestSchedulerProvider
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.concurrent.TimeUnit

class MyJokesViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val testScheduler = TestScheduler()
    private lateinit var testSchedulerProvider: TestSchedulerProvider

    @Mock
    private lateinit var getUserJokesUseCase: GetUserJokesUseCase

    @Mock
    private lateinit var deleteJokeByIdUseCase: DeleteJokeByIdUseCase

    private lateinit var viewModel: MyJokesViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        testSchedulerProvider = TestSchedulerProvider(testScheduler)
    }

    @Test
    fun `WHEN user has liked or added jokes THEN return jokes list SUCCESS`() {
        val observer: Observer<List<Joke>> = mock()
        val list = mockJokesList()
        val mappedList = getMockedMappedList(list)

        whenever(getUserJokesUseCase.get()).thenReturn(Observable.just(mappedList))

        viewModel = MyJokesViewModel(
            getUserJokesUseCase,
            deleteJokeByIdUseCase,
            testSchedulerProvider
        )

        viewModel.userJokes.observeForever(observer)

        verify(getUserJokesUseCase).get()

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        verify(observer).onChanged(list)

        assert(viewModel.userJokes.value!!.isNotEmpty())
    }

    @Test
    fun `WHEN user does not have liked or added jokes THEN return jokes empty list SUCCESS`() {
        val observer: Observer<List<Joke>> = mock()
        val list = emptyList<Joke>()
        val mappedList = getMockedMappedList(list)

        whenever(getUserJokesUseCase.get()).thenReturn(Observable.just(mappedList))

        viewModel = MyJokesViewModel(
            getUserJokesUseCase,
            deleteJokeByIdUseCase,
            testSchedulerProvider
        )

        viewModel.userJokes.observeForever(observer)

        verify(getUserJokesUseCase).get()

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        verify(observer).onChanged(list)

        assert(viewModel.userJokes.value!!.isEmpty())
    }

    @Test
    fun `WHEN fetching liked or added jokes FAILED with ERROR`() {
        val errorObserver: Observer<String> = mock()

        whenever(getUserJokesUseCase.get()).thenReturn(Observable.error(Throwable()))
        viewModel = MyJokesViewModel(
            getUserJokesUseCase,
            deleteJokeByIdUseCase,
            testSchedulerProvider
        )

        viewModel.error.observeForever(errorObserver)

        verify(getUserJokesUseCase).get()

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)

        assert(viewModel.error.value!!.isEmpty().not())
    }

    @Test
    fun `WHEN user clicks delete THEN remove joke from db`() {
        val observer: Observer<List<Joke>> = mock()
        val list = mockJokesList()
        val mappedList = getMockedMappedList(list)

        whenever(deleteJokeByIdUseCase.complete(any())).thenReturn(Completable.complete())
        whenever(getUserJokesUseCase.get()).thenReturn(Observable.just(mappedList))

        viewModel = MyJokesViewModel(
            getUserJokesUseCase,
            deleteJokeByIdUseCase,
            testSchedulerProvider
        )

        viewModel.onDeleteJokeClicked(getJoke(1))

        viewModel.userJokes.observeForever(observer)

        verify(deleteJokeByIdUseCase).complete(any())

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        verify(observer).onChanged(list)
    }

    @Test
    fun `WHEN user clicks add joke THEN navigate to add joke screen`() {
        val list = mockJokesList()
        val mappedList = getMockedMappedList(list)

        whenever(getUserJokesUseCase.get()).thenReturn(Observable.just(mappedList))

        viewModel = MyJokesViewModel(
            getUserJokesUseCase,
            deleteJokeByIdUseCase,
            testSchedulerProvider
        )

        viewModel.onAddJokeClicked()
        assert(viewModel.navigation.value is MyJokesViewModel.Navigation.AddJoke)
    }

    private fun getMockedMappedList(list: List<Joke>): MappedList<Joke> {
        return MappedList(list)
    }

    private fun mockJokesList(): List<Joke> {
        return listOf(getJoke(1), getJoke(2), getJoke(3))
    }

    private fun getJoke(id: Long = 1, likedId: Long = -1): Joke {
        return Joke(id, "mocked joke", likedId)
    }
}