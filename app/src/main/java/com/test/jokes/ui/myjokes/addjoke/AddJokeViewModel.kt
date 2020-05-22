package com.test.jokes.ui.myjokes.addjoke

import com.test.data.rx.SchedulerProvider
import com.test.jokes.ui.base.BaseViewModel
import javax.inject.Inject

class AddJokeViewModel @Inject constructor(schedulerProvider: SchedulerProvider) :
    BaseViewModel(schedulerProvider) {

}