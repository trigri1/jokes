package com.test.data.jokes.client

import com.test.data.jokes.models.response.JokesResponseModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

const val SUCCESS: String = "success"

interface ApiClient {

    @GET("jokes?escape=javascript")
    fun getJokes(
        @Query("firstName") firstName: String? = null,
        @Query("lastName") lastName: String? = null
    ): Single<JokesResponseModel>

//    @GET("android/latest")
//    fun getCurrencyRates(@Query("base") baseCurrency: String): Observable<CurrencyRatesResponseModel>
}