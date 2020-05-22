package com.test.jokes.di.module

import com.test.jokes.ui.main.MainFragment
import com.test.jokes.ui.myjokes.MyJokesFragment
import com.test.jokes.ui.settins.SettingsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment

    @ContributesAndroidInjector
    abstract fun contributeMyJokesFragment(): MyJokesFragment

    @ContributesAndroidInjector
    abstract fun contributeSettingsFragment(): SettingsFragment
}