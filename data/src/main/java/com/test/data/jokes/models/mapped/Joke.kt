package com.test.data.jokes.models.mapped

import com.test.data.base.MappedModel

data class Joke(val id: Long, val joke: String) : MappedModel()