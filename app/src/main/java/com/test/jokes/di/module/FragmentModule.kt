package com.test.jokes.di.module

import com.test.jokes.ui.jokeslist.JokesListFragment
import com.test.jokes.ui.myjokes.MyJokesFragment
import com.test.jokes.ui.myjokes.addjoke.AddJokeFragment
import com.test.jokes.ui.settings.SettingsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): JokesListFragment

    @ContributesAndroidInjector
    abstract fun contributeMyJokesFragment(): MyJokesFragment

    @ContributesAndroidInjector
    abstract fun contributeAddJokeFragment(): AddJokeFragment

    @ContributesAndroidInjector
    abstract fun contributeSettingsFragment(): SettingsFragment
}