package com.test.data.jokes.repository

import com.test.data.jokes.models.mapped.Joke
import com.test.data.jokes.models.mapped.JokesModel
import com.test.data.local.db.entities.UserJokeEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface JokesRepository {
    fun getJokesList(firstName: String? = null, lastName: String? = null): Single<JokesModel>
    fun getUserJokes(): Observable<List<Joke>>
    fun addJoke(joke: UserJokeEntity): Completable
    fun deleteJoke(joke: UserJokeEntity): Completable
    fun deleteJokeByLikedId(id: Long): Completable
    fun deleteJokeById(id: Long): Completable
}