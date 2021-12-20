package com.mrtwon.quote.domain.repository

import com.mrtwon.quote.domain.entity.QuoteEntity
import com.mrtwon.quote.domain.exception.Failure
import com.mrtwon.quote.functional.Either
import kotlinx.coroutines.CoroutineScope

interface QuoteRepository {
    fun getRandomQuote(): Either<Failure, QuoteEntity>
}