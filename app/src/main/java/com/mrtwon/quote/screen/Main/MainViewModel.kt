package com.mrtwon.quote.screen.Main

import androidx.lifecycle.*
import com.mrtwon.quote.domain.entity.QuoteEntity
import com.mrtwon.quote.domain.interactor.RandomQuoteInteractor
import javax.inject.Inject


class MainViewModel @Inject constructor(var randomQuoteInteractor: RandomQuoteInteractor)
    : BaseViewModel() {

    private val quoteEntityMutableLiveData = MutableLiveData<QuoteEntity>()
    private val loadStateMutableLiveData = MutableLiveData<Boolean>()
    val quoteEntityLiveData: LiveData<QuoteEntity> = quoteEntityMutableLiveData
    val loadStateLiveData: LiveData<Boolean> = loadStateMutableLiveData

    private fun handleResponse(quoteEntity: QuoteEntity){
        quoteEntityMutableLiveData.value = quoteEntity
    }

    fun getQuote(){
        loadStateMutableLiveData.value = true
        randomQuoteInteractor(Unit, viewModelScope) {
            loadStateMutableLiveData.value = false
            it.fold(
                ::handleFailure,
                ::handleResponse
            )
        }
    }

    class Factory @Inject constructor(val randomQuoteInteractor: RandomQuoteInteractor)
        : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(randomQuoteInteractor) as T
        }

    }
}