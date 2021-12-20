package com.mrtwon.quote.di.appComponent

import com.mrtwon.quote.data.api.QuoteApi
import com.mrtwon.quote.data.localSource.RawDataSource
import com.mrtwon.quote.data.respository.QuoteRepositoryImpl
import com.mrtwon.quote.domain.interactor.RandomQuoteInteractor
import com.mrtwon.quote.domain.repository.QuoteRepository
import com.mrtwon.quote.manager.INetworkManager
import dagger.Module
import dagger.Provides

@Module(includes = [NetworkModule::class, LocalDataSourceModule::class])
class RepositoryModule {

    @AppScope
    @Provides
    fun repositoryQuote(api: QuoteApi, localSource: RawDataSource, netManager: INetworkManager): QuoteRepository{
        return QuoteRepositoryImpl(api, localSource, netManager)
    }

}