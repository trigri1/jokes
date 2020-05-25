package com.test.jokes.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.jokes.ui.jokeslist.JokesListViewModel
import com.test.jokes.ui.main.MainViewModel
import com.test.jokes.ui.myjokes.MyJokesViewModel
import com.test.jokes.ui.myjokes.addjoke.AddJokeViewModel
import com.test.jokes.ui.settings.SettingsViewModel
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
    @ViewModelKey(JokesListViewModel::class)
    abstract fun bindMainFragViewModel(jokesListViewModel: JokesListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MyJokesViewModel::class)
    abstract fun bindMyJokesViewModel(myJokesViewModel: MyJokesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddJokeViewModel::class)
    abstract fun bindAddJokesViewModel(addJokeViewModel: AddJokeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun bindSettingsViewModel(settingsViewModel: SettingsViewModel): ViewModel
}