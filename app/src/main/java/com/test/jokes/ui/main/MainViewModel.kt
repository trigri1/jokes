package com.test.jokes.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.test.data.rx.SchedulerProvider
import com.test.jokes.ui.base.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(schedulerProvider: SchedulerProvider) :
    BaseViewModel(schedulerProvider) {

    private val _navigation = MutableLiveData<Navigation>()
    val navigation: LiveData<Navigation> = _navigation

    fun onMainSelected() {

    }

    fun onMyJokesSelected() {

    }

    fun onSettingsSelected() {

    }

    sealed class Navigation {

    }
}