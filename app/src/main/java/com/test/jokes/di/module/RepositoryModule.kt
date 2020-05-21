package com.test.jokes.di.module


import com.test.data.jokes.repository.JokesRepository
import com.test.data.jokes.repository.JokesRepositoryImpl
import dagger.Binds
import dagger.Module

@Module(includes = [AppModule::class])
abstract class RepositoryModule {

    @Binds
    abstract fun bindCurrencyRepository(repository: JokesRepositoryImpl): JokesRepository
}