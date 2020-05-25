package com.test.data.jokes.models.response

import com.squareup.moshi.JsonClass
import com.test.data.base.model.ResponseModel
import com.test.data.jokes.models.mapped.Joke

@JsonClass(generateAdapter = true)
data class JokeResponse(
    val id: Long?,
    val joke: String? = null,
    val categories: List<String?>? = null
) : ResponseModel() {
    override fun map(): Joke = Joke(id ?: 0L, joke.orEmpty())

}