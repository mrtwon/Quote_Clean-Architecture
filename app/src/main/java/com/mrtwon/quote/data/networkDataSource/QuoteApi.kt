package com.mrtwon.quote.data.networkDataSource

import com.mrtwon.quote.data.models.QuoteModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface QuoteApi {
    @GET("api/1.0/")
    fun getRandomQuote(
        @Query("method") method: String = "getQuote",
        @Query("format") format: String = "json"
    ): Call<QuoteModel>
}