package com.test.jokes.ui.myjokes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.test.data.rx.SchedulerProvider
import com.test.jokes.ui.base.BaseViewModel
import javax.inject.Inject

class MyJokesViewModel @Inject constructor(schedulerProvider: SchedulerProvider) :
    BaseViewModel(schedulerProvider) {

    private val _navigation = MutableLiveData<String>()
    val navigation: LiveData<String> = _navigation


}