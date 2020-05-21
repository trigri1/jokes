package com.test.data.jokes.repository

import com.test.data.jokes.models.mapped.JokesModel
import com.test.data.jokes.models.response.JokesResponseModel
import io.reactivex.Single

interface JokesRepository {
    fun getJokes(firstName: String? = null, lastName: String? = null): Single<JokesModel>
}