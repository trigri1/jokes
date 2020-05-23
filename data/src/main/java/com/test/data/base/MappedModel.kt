package com.test.data.base

abstract class MappedModel
data class MappedList<T : MappedModel>(val list: List<T>) : MappedModel()