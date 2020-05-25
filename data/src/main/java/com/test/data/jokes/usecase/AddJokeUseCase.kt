package com.test.data.jokes.usecase

import com.test.data.base.usecase.CompletableUseCase
import com.test.data.base.usecase.UseCase
import com.test.data.jokes.repository.JokesRepository
import com.test.data.local.db.entities.UserJokeEntity
import io.reactivex.Completable
import javax.inject.Inject

class AddJokeUseCase @Inject constructor(private val repository: JokesRepository) :
    CompletableUseCase<AddJokeUseCase.Args> {

    override fun complete(args: Args): Completable {
        val joke = UserJokeEntity(joke = args.joke, likedId = args.likedId)
        return repository.addJoke(joke)
    }

    data class Args(val joke: String, val likedId: Long = 0) :
        UseCase.Args()
}