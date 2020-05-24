package com.test.jokes.ui.myjokes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.test.data.jokes.models.mapped.Joke
import com.test.data.jokes.usecase.DeleteJokeUseCase
import com.test.data.jokes.usecase.GetUserJokesUseCase
import com.test.data.rx.SchedulerProvider
import com.test.jokes.ui.base.BaseViewModel
import javax.inject.Inject

class MyJokesViewModel @Inject constructor(
    private val getUserJokesUseCase: GetUserJokesUseCase,
    private val deleteJokeUseCase: DeleteJokeUseCase,
    schedulerProvider: SchedulerProvider
) : BaseViewModel(schedulerProvider) {

    private val _userJokes = MutableLiveData<List<Joke>>()
    val userJokes: LiveData<List<Joke>> = _userJokes

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _navigation = MutableLiveData<Navigation>()
    val navigation: LiveData<Navigation> = _navigation

    init {
        getUserJokesList()
    }

    fun onAddJokeClicked() {
        _navigation.postValue(Navigation.AddJoke)
    }

    fun onDeleteJokeClicked(joke: Joke) {
        deleteJokeUseCase.complete(DeleteJokeUseCase.Args(joke.id))
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe()
            .addToDisposable()
    }

    private fun getUserJokesList() {
        getUserJokesUseCase.get()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(
                {
                    handleResponse(it.list)
                },
                { throwable ->
                    handleError(throwable)
                }).addToDisposable()

    }

    private fun handleResponse(list: List<Joke>) {
        _userJokes.postValue(list)
    }

    private fun handleError(throwable: Throwable) {
        _error.postValue(getError(throwable))
    }

    sealed class Navigation {
        object AddJoke : Navigation()
    }

}