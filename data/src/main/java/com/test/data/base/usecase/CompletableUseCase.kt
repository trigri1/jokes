package com.test.data.base.usecase

import io.reactivex.Completable

interface CompletableUseCase<A : UseCase.Args> {
    fun complete(args: A): Completable
}