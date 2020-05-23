package com.test.data.jokes.repository

import com.test.data.jokes.client.ApiClient
import com.test.data.jokes.client.SUCCESS
import com.test.data.jokes.models.mapped.Joke
import com.test.data.jokes.models.mapped.JokesModel
import com.test.data.local.db.AppDataBase
import com.test.data.local.db.entities.UserJokeEntity
import com.test.data.local.prefs.PrefsHelper
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JokesRepositoryImpl @Inject constructor(
    private val apiClient: ApiClient,
    private val database: AppDataBase,
    private val prefsHelper: PrefsHelper
) : JokesRepository {

    override fun getJokes(firstName: String?, lastName: String?): Single<JokesModel> {
        return apiClient.getJokes()
            .map {
                if (SUCCESS != it.type) {
                    throw  RuntimeException("Could not retrieve Jokes")
                }
                it.map()
            }
    }

    override fun getUserJokes(): Observable<List<Joke>> {
        return database.getUserJokesDao().getAllUserJokes()
            .map {
                it.map { userJoke ->
                    userJoke.map()
                }
            }
    }

    override fun addJokes(joke: UserJokeEntity): Completable {
        return database.getUserJokesDao().insert(joke)
    }

    override fun deleteJokes(joke: UserJokeEntity): Completable {
        return database.getUserJokesDao().delete(joke)
    }
}