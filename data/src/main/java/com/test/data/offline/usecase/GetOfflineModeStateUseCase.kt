package com.test.data.offline.usecase

import com.test.data.base.usecase.NoArgUseCase
import com.test.data.offline.model.OfflineStateModel
import com.test.data.offline.repository.OfflineRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetOfflineModeStateUseCase @Inject constructor(private val offlineRepository: OfflineRepository) :
    NoArgUseCase<OfflineStateModel> {
    override fun get(): Observable<OfflineStateModel> {
        return offlineRepository.getOfflineState().map {
            OfflineStateModel(it)
        }
    }
}