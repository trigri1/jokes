package com.test.data.offline.usecase

import com.test.data.base.usecase.CompletableUseCase
import com.test.data.base.usecase.UseCase
import com.test.data.offline.repository.OfflineRepository
import io.reactivex.Completable
import javax.inject.Inject

class SetOfflineModeStateUseCase @Inject constructor(private val offlineRepository: OfflineRepository) :
    CompletableUseCase<SetOfflineModeStateUseCase.Args> {

    data class Args(val offlineState: Boolean) : UseCase.Args()

    override fun complete(args: Args): Completable {
        return offlineRepository.setOfflineState(args.offlineState)
    }
}