package com.test.data.base.model

import com.test.data.base.model.MappedModel

abstract class ResponseModel {
    abstract fun map(): MappedModel
}