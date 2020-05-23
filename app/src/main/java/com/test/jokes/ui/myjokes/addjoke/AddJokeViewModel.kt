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

    fun onSaveJokeClicked(joke: String?) {
        if (joke.isNullOrEmpty()) {
            _error.postValue("Joke should not be empty")

        } else {
            addJokeUseCase.complete(AddJokeUseCase.Args(joke.orEmpty()))
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe {

                }.addToDisposable()
        }
    }

}