package com.mrtwon.quote.data.networkDataSource

import com.mrtwon.quote.data.models.QuoteModel
import javax.inject.Inject

class NetworkDataSourceImpl @Inject constructor(val quoteApi: QuoteApi): NetworkDataSource {
    override fun executeRandomQuote(): NetworkResponse<QuoteModel> {
        val response = quoteApi.getRandomQuote().execute()
        return NetworkResponse(response.isSuccessful, response.body())
    }
}