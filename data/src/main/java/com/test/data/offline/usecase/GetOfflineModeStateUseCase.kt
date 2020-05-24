package com.test.data.offline.usecase

import com.test.data.base.usecase.NoArgSingleUseCase
import com.test.data.offline.model.OfflineStateModel
import com.test.data.offline.repository.OfflineRepository
import io.reactivex.Single
import javax.inject.Inject

class GetOfflineModeStateUseCase @Inject constructor(private val offlineRepository: OfflineRepository) :
    NoArgSingleUseCase<OfflineStateModel> {
    override fun get(): Single<OfflineStateModel> {
        return offlineRepository.getOfflineState().map {
            OfflineStateModel(it)
        }
    }
}