package com.test.data.jokes.models.response

import com.squareup.moshi.JsonClass
import com.test.data.base.model.ResponseModel
import com.test.data.jokes.models.mapped.JokesModel

@JsonClass(generateAdapter = true)
data class JokesResponseModel(
    val type: String? = null,
    val value: List<JokeResponse>? = null
) : ResponseModel() {
    override fun map(): JokesModel {
        val list = value?.map { it.map() }
        return JokesModel(type ?: "", list ?: emptyList())
    }
}