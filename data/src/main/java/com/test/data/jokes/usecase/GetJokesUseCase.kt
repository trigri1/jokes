package com.test.data.jokes.usecase

import com.test.data.base.usecase.UseCase
import com.test.data.jokes.models.mapped.JokesModel
import com.test.data.jokes.repository.JokesRepository
import io.reactivex.Single
import javax.inject.Inject

class GetJokesUseCase @Inject constructor(private val jokesRepository: JokesRepository) :
    UseCase<GetJokesUseCase.Args, JokesModel> {

    override fun get(args: Args): Single<JokesModel> {
        return jokesRepository.getJokesList(args.firstName, args.lastName)
    }

    data class Args(val firstName: String = "Chuck", val lastName: String = "Norris") :
        UseCase.Args()
}

