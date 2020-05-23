package com.test.jokes.ui.myjokes.addjoke

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.test.data.jokes.usecase.AddJokeUseCase
import com.test.data.rx.SchedulerProvider
import com.test.jokes.ui.base.BaseViewModel
import javax.inject.Inject

class AddJokeViewModel @Inject constructor(
    private val addJokeUseCase: AddJokeUseCase,
    schedulerProvider: SchedulerProvider
) : BaseViewModel(schedulerProvider) {

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _navigation = MutableLiveData<Navigation>()
    val navigation: LiveData<Navigation> = _navigation

    fun onSaveJokeClicked(joke: String?) {
        if (joke.isNullOrEmpty()) {
            _error.postValue("Joke should not be empty")

        } else {
            addJokeUseCase.complete(AddJokeUseCase.Args(joke.orEmpty()))
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe {
                    _navigation.postValue(Navigation.MyJokes)
                }.addToDisposable()
        }
    }

    fun onCancelClicked() {
        _navigation.postValue(Navigation.MyJokes)
    }

    sealed class Navigation {
        object MyJokes : Navigation()
    }

}