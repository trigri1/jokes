package com.test.data.offline.repository

import com.test.data.offline.model.CharacterName
import io.reactivex.Completable
import io.reactivex.Single

interface OfflineRepository {
    fun getOfflineState(): Single<Boolean>
    fun setOfflineState(offlineState: Boolean): Completable
    fun getCharacterName(): Single<CharacterName>
    fun setCharacterName(name: CharacterName): Completable
}