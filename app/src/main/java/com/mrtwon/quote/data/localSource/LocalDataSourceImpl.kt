package com.mrtwon.quote.data.localSource

import com.mrtwon.quote.data.models.QuoteModel
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(val rawDataSource: RawDataSource): LocalDataSource {
    override fun executeRandomQuote(): QuoteModel {
        return rawDataSource.randomQuote()
    }
}