package com.mrtwon.quote.di.appComponent

import com.mrtwon.quote.data.api.QuoteApi
import com.mrtwon.quote.manager.INetworkManager
import com.mrtwon.quote.manager.NetworkManager
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module()
class NetworkModule {

    @AppScope
    @Provides
    fun provideNetworkState(): INetworkManager{
        return NetworkManager.instance()
    }

    @AppScope
    @Provides
    fun api(retrofit: Retrofit): QuoteApi {
        return retrofit.create(QuoteApi::class.java)
    }

    @AppScope
    @Provides
    fun retrofit(client: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .baseUrl("http://api.forismatic.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @AppScope
    @Provides
    fun okHttpClient(): OkHttpClient{
        return OkHttpClient.Builder().build()
    }

}