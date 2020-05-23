package com.test.data.base

import io.reactivex.Single

interface NoArgUseCase<R : MappedModel> {
    fun get(): Single<R>
}