package com.test.jokes.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.test.data.jokes.usecase.GetJokesListUseCase
import com.test.data.offline.model.CharacterName
import com.test.data.offline.usecase.GetCharacterNameUseCase
import com.test.data.offline.usecase.GetOfflineModeStateUseCase
import com.test.data.offline.usecase.SetCharacterNameUseCase
import com.test.data.offline.usecase.SetOfflineModeStateUseCase
import com.test.data.rx.SchedulerProvider
import com.test.jokes.ui.base.BaseViewModel
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val getOfflineModeStateUseCase: GetOfflineModeStateUseCase,
    private val setOfflineModeStateUseCase: SetOfflineModeStateUseCase,
    private val getCharacterNameUseCase: GetCharacterNameUseCase,
    private val setCharacterNameUseCase: SetCharacterNameUseCase,
    schedulerProvider: SchedulerProvider
) : BaseViewModel(schedulerProvider) {

    private val _characterName = MutableLiveData<CharacterName>()
    val characterName: LiveData<CharacterName> = _characterName

    private val _offlineMode = MutableLiveData<Boolean>()
    val offlineMode: LiveData<Boolean> = _offlineMode

    init {
        getOfflineState()
        getCharacterName()
    }

    fun setCharacterName(firstName: String, lastName: String) {
        setCharacterNameUseCase.complete(SetCharacterNameUseCase.Args(firstName, lastName))
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe {

            }.addToDisposable()
    }

    fun onOfflineModeChanged(offlineMode: Boolean) {
        setOfflineModeStateUseCase.complete(SetOfflineModeStateUseCase.Args(offlineMode))
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe()
            .addToDisposable()
    }

    private fun getCharacterName() {
        getCharacterNameUseCase.get()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({
                _characterName.postValue(it)
            }, {

            }).addToDisposable()
    }

    private fun getOfflineState() {
        getOfflineModeStateUseCase.get()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(
                { _offlineMode.postValue(it.useOffline) }, {}
            ).addToDisposable()
    }
}