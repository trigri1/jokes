package com.test.data.jokes.models.mapped

import com.test.data.base.model.MappedModel

data class JokesModel(val type: String, val value: List<Joke>) : MappedModel()