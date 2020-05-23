package com.test.data.offline.repository

import com.test.data.local.prefs.PrefsHelper
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfflineRepositoryImpl @Inject constructor(private val prefsHelper: PrefsHelper) :
    OfflineRepository {

    override fun getOfflineState(): Observable<Boolean> {
        return Observable.just(prefsHelper.offlineModeEnabled)
    }

    override fun setOfflineState(offlineState: Boolean): Completable {
        prefsHelper.offlineModeEnabled = offlineState
        return Completable.complete()
    }
}