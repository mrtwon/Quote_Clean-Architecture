package com.mrtwon.quote.data.repository


import com.mrtwon.quote.data.networkDataSource.NetworkDataSource
import com.mrtwon.quote.data.networkDataSource.NetworkResponse
import com.mrtwon.quote.data.localSource.LocalDataSource
import com.mrtwon.quote.domain.entity.QuoteEntity

import com.mrtwon.quote.domain.exception.Failure
import com.mrtwon.quote.domain.repository.QuoteRepository
import com.mrtwon.quote.functional.Either
import com.mrtwon.quote.manager.INetworkManager
import java.lang.Exception
import javax.inject.Inject

class QuoteRepositoryImpl @Inject constructor(
    var networkSource: NetworkDataSource,
    var localSource: LocalDataSource,
    val networkManager: INetworkManager) : QuoteRepository{

    override fun getRandomQuote(): Either<Failure, QuoteEntity> {
        return branchRequest(
            {
                // network data source call
                requestNetworkSource(
                    { networkSource.executeRandomQuote() },
                    { it.toQuoteEntity() }
                )
            },
            {
                // local data source call
                requestLocalDataSource(
                    { localSource.executeRandomQuote() },
                    { it.toQuoteEntity() }
                )
            }
        )
    }


    /* branching request */

    private fun <R> branchRequest(
        callNetSource: () -> Either<Failure,R>, callLocalSource: () -> Either<Failure, R>
    ): Either<Failure, R>{
        return when(networkManager.isConnect()){
            true -> callNetSource()
            false -> callLocalSource()
        }
    }


    /* Request Function */

    // local  request
    private fun <T, R> requestLocalDataSource(call: () -> T, transform: (T) -> R): Either<Failure, R>{
        return try{
            val result = call()
            Either.Right(transform(result))
        }catch (e: Exception){
            Either.Left(Failure.ReadError)
        }
    }

    private fun <T, R> requestNetworkSource(call: () -> NetworkResponse<T>, transform: (T) -> R)
    : Either<Failure, R>{
        return try{
            val result = call()
            when(result.isSuccessful){
                true -> Either.Right(transform(result.response!!))
                false -> Either.Left(Failure.ServerError)
            }
        }catch (e: Exception){
            return Either.Left(Failure.NetworkConnection)
        }
    }


    /*// network request
    private fun <T, R> request(
        call: Call<T>,
        transform: (T) -> R,
        default : T
    ): Either<Failure, R>{
        return try{
            val response = call.execute() *//* val response = call() *//*
            when(response.isSuccessful){
                true -> Either.Right(transform(response.body() ?: default))
                false -> Either.Left(Failure.ServerError)
            }
        }catch (e: Exception){
            e.printStackTrace()
            return Either.Left(Failure.ServerError)
        }
    }*/
}