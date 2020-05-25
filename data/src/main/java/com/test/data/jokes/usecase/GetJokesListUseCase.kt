package com.test.data.jokes.usecase

import com.test.data.base.model.MappedList
import com.test.data.base.usecase.UseCase
import com.test.data.jokes.models.mapped.Joke
import com.test.data.jokes.repository.JokesRepository
import com.test.data.offline.repository.FIRST_NAME
import com.test.data.offline.repository.LAST_NAME
import io.reactivex.Single
import javax.inject.Inject

class GetJokesListUseCase @Inject constructor(private val jokesRepository: JokesRepository) :
    UseCase<GetJokesListUseCase.Args, MappedList<Joke>> {

    override fun get(args: Args): Single<MappedList<Joke>> {
        return jokesRepository.getJokesList(args.refresh, args.firstName, args.lastName)
            .map {list->
                MappedList(list)
            }
    }

    data class Args(
        val refresh: Boolean,
        val firstName: String = FIRST_NAME,
        val lastName: String = LAST_NAME
    ) : UseCase.Args()

}

