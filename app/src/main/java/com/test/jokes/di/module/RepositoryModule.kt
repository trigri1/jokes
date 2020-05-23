package com.test.jokes.di.module


import com.test.data.jokes.repository.JokesRepository
import com.test.data.jokes.repository.JokesRepositoryImpl
import com.test.data.offline.repository.OfflineRepository
import com.test.data.offline.repository.OfflineRepositoryImpl
import dagger.Binds
import dagger.Module

@Module(includes = [AppModule::class])
abstract class RepositoryModule {

    @Binds
    abstract fun bindJokesRepository(repository: JokesRepositoryImpl): JokesRepository

    @Binds
    abstract fun bindOfflineRepository(offlineRepository: OfflineRepositoryImpl): OfflineRepository
}