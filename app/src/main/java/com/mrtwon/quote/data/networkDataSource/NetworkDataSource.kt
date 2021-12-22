package com.mrtwon.quote.data.networkDataSource

import com.mrtwon.quote.data.models.QuoteModel

interface NetworkDataSource {
    fun executeRandomQuote(): NetworkResponse<QuoteModel>
}