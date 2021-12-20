package com.mrtwon.quote.screen.Main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mrtwon.quote.domain.exception.Failure

open class BaseViewModel: ViewModel() {
    private val failureMutableLiveData = MutableLiveData<Failure>()
    val failureLiveData: LiveData<Failure> = failureMutableLiveData

    open fun handleFailure(failure: Failure){
        failureMutableLiveData.value = failure
    }
}