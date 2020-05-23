package com.test.data.base.usecase

import com.test.data.base.model.MappedModel
import io.reactivex.Observable

interface NoArgUseCase<R : MappedModel> {
    fun get(): Observable<R>
}