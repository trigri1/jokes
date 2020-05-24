package com.test.data.jokes.usecase

import com.test.data.base.model.MappedList
import com.test.data.base.usecase.NoArgSingleUseCase
import com.test.data.jokes.models.mapped.Joke
import com.test.data.jokes.repository.JokesRepository
import com.test.data.offline.usecase.GetCharacterNameUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetRandomJokeUseCase @Inject constructor(
    private val jokesRepository: JokesRepository,
    private val getCharacterNameUseCase: GetCharacterNameUseCase
) : NoArgSingleUseCase<MappedList<Joke>> {
    override fun get(): Single<MappedList<Joke>> {
        return getCharacterNameUseCase.get()
            .flatMap {
                jokesRepository.getRandomJoke(it.firstName, it.lastName)
            }.map { list ->
                MappedList(list)
            }
    }
}

