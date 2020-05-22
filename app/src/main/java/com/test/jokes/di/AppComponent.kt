package com.test.jokes.di

import com.test.jokes.di.module.DatabaseModule
import com.test.jokes.JokesApp
import com.test.jokes.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        FragmentModule::class,
        ActivityModule::class,
        ViewModelModule::class,
        RepositoryModule::class,
        DatabaseModule::class]
)
interface AppComponent : AndroidInjector<JokesApp> {

    override fun inject(instance: JokesApp?)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: JokesApp): Builder

        fun build(): AppComponent
    }
}