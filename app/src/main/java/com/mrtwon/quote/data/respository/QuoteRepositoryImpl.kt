package com.mrtwon.quote.data.respository


import com.mrtwon.quote.data.api.QuoteApi
import com.mrtwon.quote.data.localSource.RawDataSource
import com.mrtwon.quote.data.models.QuoteModel
import com.mrtwon.quote.domain.entity.QuoteEntity

import com.mrtwon.quote.domain.exception.Failure
import com.mrtwon.quote.domain.repository.QuoteRepository
import com.mrtwon.quote.functional.Either
import com.mrtwon.quote.manager.INetworkManager
import com.mrtwon.quote.manager.NetworkManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import java.lang.Exception
import javax.inject.Inject

class QuoteRepositoryImpl @Inject constructor(var api: QuoteApi, var localSource: RawDataSource,
val networkManager: INetworkManager)
    : QuoteRepository{


    override fun getRandomQuote(): Either<Failure, QuoteEntity> {
        return branchRequest(
            {
                // network data source call
                request(
                    call = api.getRandomQuote(),
                    transform = { it.toQuoteEntity() },
                    default = QuoteModel.empty
                )
            },
            {
                // local data source call
                request({ localSource.randomQuote() }, { it.toQuoteEntity() })
            }
        )
    }


    // branching request
    private fun <R> branchRequest(
        callNetSource: () -> Either<Failure,R>, callLocalSource: () -> Either<Failure, R>
    ): Either<Failure, R>{
        return when(networkManager.isConnect()){
            true -> callNetSource()
            false -> callLocalSource()
        }
    }




    // local  request
    private fun <T, R> request(call: () -> T, transform: (T) -> R): Either<Failure, R>{
        return try{
            val result = call()
            Either.Right(transform(result))
        }catch (e: Exception){
            Either.Left(Failure.ReadError)
        }
    }

    // network request
    private fun <T, R> request(
        call: Call<T>,
        transform: (T) -> R,
        default : T
    ): Either<Failure, R>{
        return try{
            val response = call.execute()
            when(response.isSuccessful){
                true -> Either.Right(transform(response.body() ?: default))
                false -> Either.Left(Failure.ServerError)
            }
        }catch (e: Exception){
            e.printStackTrace()
            return Either.Left(Failure.ServerError)
        }
    }
}