package com.test.jokes.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.test.data.jokes.models.mapped.Joke
import com.test.data.jokes.models.mapped.JokesModel
import com.test.data.jokes.usecase.AddJokeUseCase
import com.test.data.jokes.usecase.DeleteJokeByIdUseCase
import com.test.data.jokes.usecase.GetJokesUseCase
import com.test.data.rx.SchedulerProvider
import com.test.jokes.ui.base.BaseViewModel
import javax.inject.Inject

class MainFragViewModel @Inject constructor(
    private val getJokesUseCase: GetJokesUseCase,
    private val addJokeUseCase: AddJokeUseCase,
    private val deleteJokeByIdUseCase: DeleteJokeByIdUseCase,
    schedulerProvider: SchedulerProvider
) : BaseViewModel(schedulerProvider) {

    private val _jokesItem = MutableLiveData<JokesModel>()
    val jokesItem: LiveData<JokesModel> = _jokesItem

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _share = MutableLiveData<String>()
    val share: LiveData<String> = _share

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private lateinit var jokesModel: JokesModel

    init {
        getJokes()
    }

    fun onLikeClicked(jokeModel: Joke) {
        addJokeUseCase.complete(AddJokeUseCase.Args(jokeModel.joke, jokeModel.id))
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe()
            .addToDisposable()
    }

    fun onUnLikeJokeClicked(id: Long) {
        deleteJokeByIdUseCase.complete(DeleteJokeByIdUseCase.Args(id))
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe()
            .addToDisposable()
    }

    fun onShareClicked(joke: String) {
        _share.postValue(joke)
    }

    private fun getJokes() {
        getJokesUseCase.get(GetJokesUseCase.Args())
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .doOnSubscribe { _loading.postValue(true) }
            .doFinally { _loading.postValue(false) }
            .subscribe({ jokes ->
                _jokesItem.postValue(jokes)
            }, { throwable ->
                _error.postValue(getError(throwable))
            }).addToDisposable()
    }
}