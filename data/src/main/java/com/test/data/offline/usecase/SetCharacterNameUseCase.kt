package com.test.data.offline.usecase

import com.test.data.base.usecase.CompletableUseCase
import com.test.data.base.usecase.UseCase
import com.test.data.offline.model.CharacterName
import com.test.data.offline.repository.OfflineRepository
import io.reactivex.Completable
import javax.inject.Inject

class SetCharacterNameUseCase @Inject constructor(private val offlineRepository: OfflineRepository) :
    CompletableUseCase<SetCharacterNameUseCase.Args> {

    data class Args(val firstName: String, val lastName: String) : UseCase.Args()

    override fun complete(args: Args): Completable {
        return offlineRepository.setCharacterName(CharacterName(args.firstName, args.lastName))
    }
}