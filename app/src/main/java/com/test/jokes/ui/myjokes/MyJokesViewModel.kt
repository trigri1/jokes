package com.test.jokes.ui.myjokes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.test.data.jokes.models.mapped.Joke
import com.test.data.jokes.repository.JokesRepositoryImpl.NoRecordException
import com.test.data.jokes.usecase.GetUserJokesUseCase
import com.test.data.rx.SchedulerProvider
import com.test.jokes.ui.base.BaseViewModel
import javax.inject.Inject

class MyJokesViewModel @Inject constructor(
    private val getUserJokesUseCase: GetUserJokesUseCase,
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

    private fun getUserJokesList() {
        getUserJokesUseCase.get()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(
                {
                    _userJokes.postValue(it.list)
                },
                { throwable ->
                    handleError(throwable)
                }).addToDisposable()

    }

    private fun handleError(throwable: Throwable) {
        val errorMsg = if (throwable is NoRecordException) {
            throwable.message
        } else {
            getError(throwable)
        }
        _error.postValue(errorMsg)
    }

    sealed class Navigation {
        object AddJoke : Navigation()
    }

}