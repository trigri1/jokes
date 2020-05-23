package com.test.data.base

import io.reactivex.Observable

interface NoArgUseCase<R : MappedModel> {
    fun get(): Observable<R>
}