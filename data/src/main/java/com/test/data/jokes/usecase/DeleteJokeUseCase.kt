package com.test.data.jokes.usecase

import com.test.data.base.usecase.CompletableUseCase
import com.test.data.base.usecase.UseCase
import com.test.data.jokes.repository.JokesRepository
import com.test.data.local.db.entities.UserJokeEntity
import io.reactivex.Completable
import javax.inject.Inject

class DeleteJokeUseCase @Inject constructor(private val repository: JokesRepository) :
    CompletableUseCase<DeleteJokeUseCase.Args> {

    override fun complete(args: Args): Completable {
        val joke = UserJokeEntity(id = args.id.toInt(), joke = args.joke)
        return repository.deleteJoke(joke)
    }

    data class Args(val id: Long, val joke: String) : UseCase.Args()
}