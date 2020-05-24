package com.test.data.offline.usecase

import com.test.data.base.usecase.NoArgSingleUseCase
import com.test.data.offline.model.CharacterName
import com.test.data.offline.repository.OfflineRepository
import io.reactivex.Single
import javax.inject.Inject

class GetCharacterNameUseCase @Inject constructor(private val offlineRepository: OfflineRepository) :
    NoArgSingleUseCase<CharacterName> {
    override fun get(): Single<CharacterName> {
        return offlineRepository.getCharacterName()
    }
}