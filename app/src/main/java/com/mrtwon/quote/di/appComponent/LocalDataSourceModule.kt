package com.mrtwon.quote.di.appComponent

import android.content.Context
import com.mrtwon.quote.data.localSource.RawDataSource
import dagger.Module
import dagger.Provides

@Module
class LocalDataSourceModule {

    @AppScope
    @Provides
    fun provideRawDataSource(context: Context): RawDataSource{
        return RawDataSource(context)
    }

}