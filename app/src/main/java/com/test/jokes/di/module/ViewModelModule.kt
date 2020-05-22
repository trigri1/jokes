package com.test.jokes.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.jokes.ui.main.MainFragViewModel
import com.test.jokes.ui.main.MainViewModel
import com.test.jokes.ui.myjokes.MyJokesViewModel
import com.test.jokes.ui.settins.SettingsViewModel
import com.test.jokes.utils.ViewModelFactory
import com.test.jokes.utils.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ActivityModule::class])
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainFragViewModel::class)
    abstract fun bindMainFragViewModel(mainFragViewModel: MainFragViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMyJokesViewModel(myJokesViewModel: MyJokesViewModel): MyJokesViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindSettingsViewModel(settingsViewModel: SettingsViewModel): SettingsViewModel
}