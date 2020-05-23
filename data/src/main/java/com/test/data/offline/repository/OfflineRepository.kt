package com.test.data.offline.repository

import io.reactivex.Completable
import io.reactivex.Observable

interface OfflineRepository {

    fun getOfflineState(): Observable<Boolean>
    fun setOfflineState(offlineState: Boolean): Completable
}