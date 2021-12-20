package com.mrtwon.quote.manager

import androidx.lifecycle.LiveData

interface INetworkManager {
    fun isConnect(): Boolean
    fun connectLiveData(): LiveData<NetworkState>
}