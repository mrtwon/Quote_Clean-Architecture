package com.mrtwon.quote.domain.interactor


import com.mrtwon.quote.domain.entity.QuoteEntity
import com.mrtwon.quote.domain.exception.Failure
import com.mrtwon.quote.domain.repository.QuoteRepository
import com.mrtwon.quote.domain.usecase.UseCase
import com.mrtwon.quote.functional.Either
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject


class RandomQuoteInteractor @Inject constructor(var quoteRepository: QuoteRepository)
    : UseCase<QuoteEntity, Unit>(){
    override suspend fun run(params: Unit): Either<Failure, QuoteEntity>
    = quoteRepository.getRandomQuote()
}