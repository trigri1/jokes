package com.test.data.base

import io.reactivex.Single

interface UseCase<A : UseCase.Args, R : MappedModel> {
    fun get(args: A): Single<R>

    open class Args
}