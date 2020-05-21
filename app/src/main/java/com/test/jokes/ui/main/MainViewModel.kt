package com.test.jokes.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.test.data.jokes.models.mapped.JokesModel
import com.test.data.jokes.usecase.GetJokesUseCase
import com.test.data.rx.SchedulerProvider
import com.test.jokes.ui.base.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getJokesUseCase: GetJokesUseCase,
    schedulerProvider: SchedulerProvider
) : BaseViewModel(schedulerProvider) {

    private val _jokesItem = MutableLiveData<JokesModel>()
    val jokesItem: LiveData<JokesModel> = _jokesItem

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private lateinit var jokesModel: JokesModel

    init {
        getJokes()
    }

    private fun getJokes() {
        getJokesUseCase.get(GetJokesUseCase.Args())
            .subscribeOn(schedulerProvider.io()).observeOn(schedulerProvider.ui())
            .subscribe({ jokes ->
                _jokesItem.postValue(jokes)
            }, { throwable ->
                _error.postValue(getError(throwable))
            }).addToDisposable()
    }
}