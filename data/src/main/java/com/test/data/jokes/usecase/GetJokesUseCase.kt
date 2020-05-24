package com.test.data.jokes.usecase

import com.test.data.base.model.MappedList
import com.test.data.base.usecase.UseCase
import com.test.data.jokes.models.mapped.Joke
import com.test.data.offline.usecase.GetOfflineModeStateUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetJokesUseCase @Inject constructor(
    private val getJokesListUseCase: GetJokesListUseCase,
    private val getRandomJokeUseCase: GetRandomJokeUseCase,
    private val getOfflineModeStateUseCase: GetOfflineModeStateUseCase
) : UseCase<GetJokesUseCase.Args, MappedList<Joke>> {

    override fun get(args: Args): Single<MappedList<Joke>> {
        return getOfflineModeStateUseCase.get().flatMap {
            if (it.useOffline) {
                getRandomJokeUseCase.get()
            } else {
                getJokesListUseCase.get(GetJokesListUseCase.Args(args.refresh))
            }
        }
    }

    data class Args(
        val refresh: Boolean,
        val firstName: String = "Chuck",
        val lastName: String = "Norris"
    ) : UseCase.Args()
}

