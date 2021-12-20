package com.mrtwon.quote.di.appComponent

import android.content.Context
import com.mrtwon.quote.data.api.QuoteApi
import com.mrtwon.quote.data.localSource.RawDataSource
import com.mrtwon.quote.di.mainScreenComponent.MainScreenComponent
import com.mrtwon.quote.domain.repository.QuoteRepository
import com.mrtwon.quote.manager.INetworkManager
import dagger.BindsInstance
import dagger.Component
import dagger.Provides

@AppScope
@Component(modules = [NetworkModule::class, RepositoryModule::class, LocalDataSourceModule::class])
interface AppComponent {

    fun provideINetworkManager(): INetworkManager

    fun provideLocalDataSource(): RawDataSource

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