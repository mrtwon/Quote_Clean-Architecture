package com.mrtwon.quote.data.localSource

import com.mrtwon.quote.data.models.QuoteModel

interface LocalDataSource {
    fun executeRandomQuote(): QuoteModel
}