package com.test.jokes.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.test.data.base.model.MappedList
import com.test.data.jokes.models.mapped.Joke
import com.test.data.jokes.usecase.AddJokeUseCase
import com.test.data.jokes.usecase.DeleteJokeByIdUseCase
import com.test.data.jokes.usecase.GetJokesUseCase
import com.test.data.jokes.usecase.GetUserJokesUseCase
import com.test.data.rx.SchedulerProvider
import com.test.jokes.ui.base.BaseViewModel
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class MainFragViewModel @Inject constructor(
    private val getJokesUseCase: GetJokesUseCase,
    private val getUserJokesUseCase: GetUserJokesUseCase,
    private val addJokeUseCase: AddJokeUseCase,
    private val deleteJokeByIdUseCase: DeleteJokeByIdUseCase,
    schedulerProvider: SchedulerProvider
) : BaseViewModel(schedulerProvider) {

    private val _jokesItem = MutableLiveData<List<Joke>>()
    val jokesItem: LiveData<List<Joke>> = _jokesItem

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _share = MutableLiveData<String>()
    val share: LiveData<String> = _share

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

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

    fun getJokes(refresh: Boolean = false) {
        val userJokesObservable = getUserJokesUseCase.get()
        val jokesObservable = getJokesUseCase.get(GetJokesUseCase.Args(refresh)).toObservable()

        userJokesObservable.zipWith(jokesObservable.map { jokesModel -> jokesModel.list },
            BiFunction { userJokesList: MappedList<Joke>, jokesList: List<Joke> ->
                return@BiFunction associateUserLikedJokes(userJokesList.list, jokesList)
            })
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

    private fun associateUserLikedJokes(userJokes: List<Joke>, jokes: List<Joke>): List<Joke> {
        val userJokesMap = userJokes.map { it.likedId to it }.toMap()
        jokes.forEach { joke ->
            if (userJokesMap.containsKey(joke.id)) {
                joke.likedId = userJokesMap[joke.id]?.likedId ?: -1
            }
        }
        return jokes
    }
}