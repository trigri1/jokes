package com.test.data.jokes.repository

import android.util.Log
import com.test.data.jokes.client.ApiClient
import com.test.data.jokes.client.SUCCESS
import com.test.data.jokes.models.mapped.Joke
import com.test.data.jokes.models.response.JokeResponse
import com.test.data.local.db.AppDataBase
import com.test.data.local.db.entities.JokeEntity
import com.test.data.local.db.entities.UserJokeEntity
import com.test.data.local.prefs.PrefsHelper
import com.test.data.offline.repository.FIRST_NAME
import com.test.data.offline.repository.LAST_NAME
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

    override fun getJokesList(
        refresh: Boolean,
        firstName: String?,
        lastName: String?
    ): Single<List<Joke>> {
        return getJokes(refresh)
    }

    override fun getRandomJoke(firstName: String?, lastName: String?): Single<List<Joke>> {
        return getRandomJokeFromDb(firstName, lastName)
    }

    override fun getUserJokes(): Observable<List<Joke>> {
        return database.getUserJokesDao().getAllUserJokes()
            .map {
                it.map { userJokes ->
                    userJokes.map()
                }
            }
    }

    override fun addJoke(joke: UserJokeEntity): Completable {
        return database.getUserJokesDao().insert(joke)
    }

    override fun deleteJoke(joke: UserJokeEntity): Completable {
        return database.getUserJokesDao().delete(joke)
    }

    override fun deleteJokeByLikedId(id: Long): Completable {
        return database.getUserJokesDao().deleteByLikedId(id)
    }

    override fun deleteJokeById(id: Long): Completable {
        return database.getUserJokesDao().deleteById(id)
    }

    private fun getRandomJokeFromDb(firstName: String?, lastName: String?): Single<List<Joke>> {
        return database.getUserJokesDao().getRandomJoke().flatMap {
            val newJoke = getReplacedJoke(it, firstName.orEmpty(), lastName.orEmpty())
            Single.just(listOf(newJoke))
        }
    }

    private fun getReplacedJoke(
        userJoke: UserJokeEntity, firstName: String, lastName: String
    ): Joke {
        return with(userJoke) {
            val isNameEmpty = firstName.isEmpty() || lastName.isEmpty()
            val newJoke = if (isNameEmpty.not()) {
                val firstNameReplaced = joke.orEmpty().replace(FIRST_NAME, firstName)
                firstNameReplaced.replace(LAST_NAME, lastName)
            } else {
                joke.orEmpty()
            }
            Joke(id.toLong(), newJoke, likedId)
        }
    }

    private fun getJokes(refresh: Boolean): Single<List<Joke>> {
        return if (refresh) {
            getJokesFromServer()
        } else {
            getPersistedJokesData().flatMap {
                if (it.isNullOrEmpty()) {
                    getJokesFromServer()
                } else {
                    Single.just(it)
                }
            }
        }
    }

    private fun getJokesFromServer(): Single<List<Joke>> {
        return apiClient.getJokes()
            .map {
                if (SUCCESS != it.type) {
                    throw  RuntimeException("Could not retrieve Jokes")
                }
                persistJokesData(it.value)
                it.map().value
            }
    }

    private fun getPersistedJokesData(): Single<List<Joke>> {
        return database.getJokesDao().getAllJokes().map {
            it.map { jokeEntity ->
                jokeEntity.map()
            }
        }
    }

    private fun persistJokesData(jokesResponseList: List<JokeResponse>?) {
        jokesResponseList?.map {
            JokeEntity(it.id ?: 0L, it.joke)
        }?.let { list ->
            database.getJokesDao().insert(list).subscribe().dispose()
        }
    }
}