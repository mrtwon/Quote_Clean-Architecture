package com.mrtwon.quote.di.appComponent

import android.content.Context
import com.mrtwon.quote.data.networkDataSource.NetworkDataSource
import com.mrtwon.quote.data.networkDataSource.QuoteApi
import com.mrtwon.quote.data.localSource.LocalDataSource
import com.mrtwon.quote.di.mainScreenComponent.MainScreenComponent
import com.mrtwon.quote.domain.repository.QuoteRepository
import com.mrtwon.quote.manager.INetworkManager
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(modules = [NetworkModule::class, RepositoryModule::class, LocalDataSourceModule::class])
interface AppComponent {

    fun provideNetworkDataSource(): NetworkDataSource

    fun provideLocalDataSource(): LocalDataSource

    fun provideINetworkManager(): INetworkManager

    fun provideQuoteApi(): QuoteApi

    fun provideRepository(): QuoteRepository

    fun mainScreenComponent(): MainScreenComponent

    @Component.Builder
    interface Builder{
        fun build(): AppComponent

        @BindsInstance
        fun addContext(context: Context): Builder
    }

}