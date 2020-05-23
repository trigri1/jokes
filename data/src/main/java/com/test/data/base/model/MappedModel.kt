package com.test.data.base.model

abstract class MappedModel
data class MappedList<T : MappedModel>(val list: List<T>) : MappedModel()