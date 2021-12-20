package com.mrtwon.quote.di.mainScreenComponent

import com.mrtwon.quote.domain.interactor.RandomQuoteInteractor
import com.mrtwon.quote.domain.repository.QuoteRepository
import com.mrtwon.quote.screen.Main.MainViewModel
import dagger.Module
import dagger.Provides

@Module
class MainScreenModule {

    @MainScreenScope
    @Provides
    fun provideRandomQuoteInteractor(quoteRepository: QuoteRepository): RandomQuoteInteractor{
        return RandomQuoteInteractor(quoteRepository)
    }

    @MainScreenScope
    @Provides
    fun provideViewModelFactory(quoteInteractor: RandomQuoteInteractor): MainViewModel.Factory{
        return MainViewModel.Factory(quoteInteractor)
    }
}