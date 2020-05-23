package com.test.data.jokes.usecase

import com.test.data.base.MappedList
import com.test.data.base.NoArgUseCase
import com.test.data.jokes.models.mapped.Joke
import com.test.data.jokes.repository.JokesRepository
import io.reactivex.Single
import javax.inject.Inject

class GetUserJokesUseCase @Inject constructor(private val jokesRepository: JokesRepository) :
    NoArgUseCase<MappedList<Joke>> {

    override fun get(): Single<MappedList<Joke>> {
        return jokesRepository.getUserJokes().map {
            MappedList(it)
        }
    }
}

