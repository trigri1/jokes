package com.test.data.offline.repository

import com.test.data.local.prefs.PrefsHelper
import com.test.data.offline.model.CharacterName
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

const val FIRST_NAME = "Chuck"
const val LAST_NAME = "Norris"

@Singleton
class OfflineRepositoryImpl @Inject constructor(private val prefsHelper: PrefsHelper) :
    OfflineRepository {

    override fun getOfflineState(): Single<Boolean> {
        return Single.just(prefsHelper.offlineModeEnabled)
    }

    override fun setOfflineState(offlineState: Boolean): Completable {
        prefsHelper.offlineModeEnabled = offlineState
        return Completable.complete()
    }

    override fun getCharacterName(): Single<CharacterName> {
        val name = with(prefsHelper) {
            CharacterName(characterFirstName ?: FIRST_NAME, characterLastName ?: LAST_NAME)
        }
        return Single.just(name)
    }

    override fun setCharacterName(name: CharacterName): Completable {
        prefsHelper.characterFirstName = name.firstName
        prefsHelper.characterLastName = name.lastName
        return Completable.complete()
    }
}