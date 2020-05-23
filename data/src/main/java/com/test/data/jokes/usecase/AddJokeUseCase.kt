package com.test.data.jokes.usecase

import com.test.data.base.CompletableUseCase
import com.test.data.base.UseCase
import com.test.data.jokes.repository.JokesRepository
import com.test.data.local.db.entities.UserJokeEntity
import io.reactivex.Completable
import javax.inject.Inject

class AddJokeUseCase @Inject constructor(private val repository: JokesRepository) :
    CompletableUseCase<AddJokeUseCase.Args> {

    override fun complete(args: Args): Completable {
        val joke = UserJokeEntity(joke = args.joke)
        return repository.addJokes(joke)
    }

    data class Args(val joke: String) : UseCase.Args()
}