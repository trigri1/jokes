package com.test.jokes.ui.jokeslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockito_kotlin.*
import com.test.data.base.model.MappedList
import com.test.data.jokes.models.mapped.Joke
import com.test.data.jokes.usecase.*
import com.test.data.offline.model.OfflineStateModel
import com.test.data.offline.usecase.GetOfflineModeStateUseCase
import com.test.data.rx.TestSchedulerProvider
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.concurrent.TimeUnit

class JokesListViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val testScheduler = TestScheduler()
    private lateinit var testSchedulerProvider: TestSchedulerProvider

    @Mock
    private lateinit var getJokesUseCase: GetJokesUseCase

    @Mock
    private lateinit var getUserJokesUseCase: GetUserJokesUseCase

    @Mock
    private lateinit var addJokeUseCase: AddJokeUseCase

    @Mock
    private lateinit var deleteJokeByIdLikedUseCase: DeleteJokeByIdLikedUseCase

    @Mock
    private lateinit var getRandomJokeUseCase: GetRandomJokeUseCase

    @Mock
    private lateinit var getOfflineModeStateUseCase: GetOfflineModeStateUseCase

    @Mock
    private lateinit var getJokesListUseCase: GetJokesListUseCase

    private lateinit var viewModel: JokesListViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        testSchedulerProvider = TestSchedulerProvider(testScheduler)
        viewModel = JokesListViewModel(
            getJokesUseCase,
            getUserJokesUseCase,
            addJokeUseCase,
            deleteJokeByIdLikedUseCase,
            testSchedulerProvider
        )
    }

    @Test
    fun `WHEN screen resumes THEN get jokes list SUCCESS`() {
        val observer: Observer<List<Joke>> = mock()
        val loadingObserver: Observer<Boolean> = mock()

        val list = mockJokesList()
        val mappedList = getMockedMappedList(list)
        whenever(getJokesUseCase.get(any())).thenReturn(Single.just(mappedList))
        whenever(getUserJokesUseCase.get()).thenReturn(Observable.just(mappedList))

        viewModel.jokesItem.observeForever(observer)
        viewModel.loading.observeForever(loadingObserver)

        viewModel.getJokes()
        verify(getJokesUseCase).get(any())

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        verify(loadingObserver).onChanged(false)
        verify(observer).onChanged(list)
    }

    @Test
    fun `WHEN user has liked or added jokes THEN list contains joke(s) with likedId != -1`() {
        val observer: Observer<List<Joke>> = mock()
        val list = mockJokesList()
        val mappedList = getMockedMappedList(list)
        val userLikedLis = MappedList(listOf(getJoke(likedId = 2)))

        whenever(getJokesUseCase.get(any())).thenReturn(Single.just(mappedList))
        whenever(getUserJokesUseCase.get()).thenReturn(Observable.just(userLikedLis))

        viewModel.jokesItem.observeForever(observer)

        viewModel.getJokes()
        verify(getJokesUseCase).get(any())

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        verify(observer).onChanged(list)

        assert(viewModel.jokesItem.value!![1].likedId > -1)
    }

    @Test
    fun `WHEN user does not have liked or added jokes THEN list contains joke(s) with likedId = -1`() {
        val observer: Observer<List<Joke>> = mock()
        val list = mockJokesList()
        val mappedList = getMockedMappedList(list)


        whenever(getJokesUseCase.get(any())).thenReturn(Single.just(mappedList))
        whenever(getUserJokesUseCase.get()).thenReturn(Observable.just(MappedList(emptyList())))

        viewModel.jokesItem.observeForever(observer)

        viewModel.getJokes()
        verify(getJokesUseCase).get(any())

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        verify(observer).onChanged(list)

        assert(viewModel.jokesItem.value!![1].likedId == -1L)
    }

    @Test
    fun `WHEN screen resumes THEN get jokes list FAIL`() {
        val list = mockJokesList()
        val mockErrorObserver: Observer<String> = mock()
        val error = "Error"
        viewModel.error.observeForever(mockErrorObserver)

        whenever(getJokesUseCase.get(any())).thenReturn(Single.error(Throwable(error)))
        whenever(getUserJokesUseCase.get()).thenReturn(Observable.just(getMockedMappedList(list)))

        viewModel.getJokes()
        verify(getJokesUseCase).get(any())

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        assert(viewModel.error.value!!.isEmpty().not())
    }

    @Test
    fun `WHEN user shakes device THEN get fresh jokes list SUCCESS`() {
        val observer: Observer<List<Joke>> = mock()
        val loadingObserver: Observer<Boolean> = mock()

        val list = mockJokesList()
        val mappedList = getMockedMappedList(list)
        whenever(getJokesUseCase.get(any())).thenReturn(Single.just(mappedList))
        whenever(getUserJokesUseCase.get()).thenReturn(Observable.just(mappedList))

        viewModel.jokesItem.observeForever(observer)
        viewModel.loading.observeForever(loadingObserver)

        viewModel.onDeviceShake()
        verify(getJokesUseCase).get(any())

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        verify(loadingObserver).onChanged(false)
        verify(observer).onChanged(list)
    }

    @Test
    fun `WHEN user shakes device THEN get fresh jokes list FAIL`() {
        val list = mockJokesList()
        val mockErrorObserver: Observer<String> = mock()
        val error = "Error"
        viewModel.error.observeForever(mockErrorObserver)

        whenever(getJokesUseCase.get(any())).thenReturn(Single.error(Throwable(error)))
        whenever(getUserJokesUseCase.get()).thenReturn(Observable.just(getMockedMappedList(list)))

        viewModel.onDeviceShake()
        verify(getJokesUseCase).get(any())

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        assert(viewModel.error.value!!.isEmpty().not())
    }

    @Test
    fun `WHEN user shakes device AND offline mode enabled THEN get random jokes from db SUCCESS`() {
        val observer: Observer<List<Joke>> = mock()
        val loadingObserver: Observer<Boolean> = mock()

        val list = listOf(getJoke(1))
        val mappedList = getMockedMappedList(list)
        whenever(getJokesUseCase.get(any())).thenReturn(Single.just(mappedList))
        whenever(getUserJokesUseCase.get()).thenReturn(Observable.just(mappedList))
        whenever(getOfflineModeStateUseCase.get()).thenReturn(Single.just(OfflineStateModel(true)))
        whenever(getRandomJokeUseCase.get()).thenReturn(Single.just(mappedList))
        whenever(getJokesListUseCase.get(any())).thenReturn(Single.just(mappedList))

        viewModel.jokesItem.observeForever(observer)
        viewModel.loading.observeForever(loadingObserver)

        viewModel.onDeviceShake()
        verify(getJokesUseCase).get(any())

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        verify(loadingObserver).onChanged(false)
        verify(observer).onChanged(list)

        verifyZeroInteractions(getJokesListUseCase)

        assert(viewModel.jokesItem.value!!.size == 1)
    }

    @Test
    fun `WHEN user likes a joke ADD joke to local db`() {
        whenever(addJokeUseCase.complete(any())).thenReturn(Completable.complete())

        val joke = getJoke()
        viewModel.onLikeClicked(joke)

        verify(addJokeUseCase).complete(any())
    }

    @Test
    fun `WHEN user unlikes a joke DELETE from local db`() {
        whenever(deleteJokeByIdLikedUseCase.complete(any())).thenReturn(Completable.complete())

        val joke = getJoke()
        viewModel.onUnLikeJokeClicked(joke.id)

        verify(deleteJokeByIdLikedUseCase).complete(any())
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