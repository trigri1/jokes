package com.test.jokes.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.test.data.offline.usecase.GetOfflineModeStateUseCase
import com.test.data.offline.usecase.SetOfflineModeStateUseCase
import com.test.data.rx.SchedulerProvider
import com.test.jokes.ui.base.BaseViewModel
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val getOfflineModeStateUseCase: GetOfflineModeStateUseCase,
    private val setOfflineModeStateUseCase: SetOfflineModeStateUseCase,
    schedulerProvider: SchedulerProvider
) : BaseViewModel(schedulerProvider) {

    private val _offlineMode = MutableLiveData<Boolean>()
    val offlineMode: LiveData<Boolean> = _offlineMode

    init {
        getOfflineState()
    }

    fun onOfflineModeChanged(offlineMode: Boolean) {
        setOfflineModeStateUseCase.complete(SetOfflineModeStateUseCase.Args(offlineMode))
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe()
            .addToDisposable()
    }

    private fun getOfflineState() {
        getOfflineModeStateUseCase.get()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe {
                _offlineMode.postValue(it.useOffline)
            }.addToDisposable()
    }
}