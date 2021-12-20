package com.mrtwon.quote.domain.usecase

import com.mrtwon.quote.domain.exception.Failure
import com.mrtwon.quote.functional.Either
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

abstract class UseCase<Type, Params> {
    abstract suspend fun run(params: Params): Either<Failure, Type>

    operator fun invoke(
        params: Params,
        scope: CoroutineScope,
        onResult: (Either<Failure, Type>) -> Unit = {}
    ) {
      scope.launch(Dispatchers.Main){
          val deferred = async(Dispatchers.IO) {
              run(params)
          }
          onResult(deferred.await())
      }
    }
}