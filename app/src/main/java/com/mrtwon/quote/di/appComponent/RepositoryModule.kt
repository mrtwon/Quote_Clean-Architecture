package com.mrtwon.quote.di.appComponent

import com.mrtwon.quote.data.networkDataSource.NetworkDataSource
import com.mrtwon.quote.data.localSource.LocalDataSource
import com.mrtwon.quote.data.repository.QuoteRepositoryImpl
import com.mrtwon.quote.domain.repository.QuoteRepository
import com.mrtwon.quote.manager.INetworkManager
import dagger.Module
import dagger.Provides

@Module(includes = [NetworkModule::class, LocalDataSourceModule::class])
class RepositoryModule {

    @AppScope
    @Provides
    fun repositoryQuote(
        netDataSource: NetworkDataSource,
        localSource: LocalDataSource,
        netManager: INetworkManager): QuoteRepository{
        return QuoteRepositoryImpl(netDataSource, localSource, netManager)
    }

}