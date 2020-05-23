package com.test.data.base

import io.reactivex.Completable

interface CompletableUseCase<A : UseCase.Args> {
    fun complete(args: A): Completable
}