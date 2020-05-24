package com.test.data.jokes.usecase

import android.util.Log
import com.test.data.base.usecase.CompletableUseCase
import com.test.data.base.usecase.UseCase
import com.test.data.jokes.repository.JokesRepository
import io.reactivex.Completable
import javax.inject.Inject

class DeleteJokeUseCase @Inject constructor(private val repository: JokesRepository) :
    CompletableUseCase<DeleteJokeUseCase.Args> {

    override fun complete(args: Args): Completable {
        return repository.deleteJokeById(args.id)
    }

    data class Args(val id: Long) : UseCase.Args()
}