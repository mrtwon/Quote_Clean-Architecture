package com.mrtwon.quote.manager

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

@SuppressLint("StaticFieldLeak")
object NetworkManager : INetworkManager  {
    private lateinit var context: Context
    private val networkCallback = NetCallback(::changeNetState)
    private val networkReceiver = NetChangeReceiver(::changeNetState)

    private var connectMutableLiveData = MutableLiveData<NetworkState>()


    fun instance(): INetworkManager{
        return this
    }

    fun init(context: Context){
        this.context = context
        registerListeners()
    }

    private fun registerListeners(){
        val cm: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            cm.registerDefaultNetworkCallback(networkCallback)
        }else{
            context.registerReceiver(networkReceiver, IntentFilter().apply {
                addAction("android.net.conn.CONNECTIVITY_CHANGE")
                }
            )
        }
    }

    override fun isConnect(): Boolean{
        return when{
            Build.VERSION.SDK_INT >= 23 -> isConnectAfterVersionM()
            else -> isConnectUpToM()
        }
    }

    override fun connectLiveData(): LiveData<NetworkState> {
        return connectMutableLiveData
    }

    private fun changeNetState(state: Boolean){
        val newNetState = when(connectLiveData().value){
            null -> NetworkState(newState = state)
            else -> NetworkState(
                oldState = connectLiveData().value!!.newState,
                newState = state
            )
        }
        connectMutableLiveData.postValue(newNetState)
    }

    // deprecated 29
    private fun isConnectUpToM(): Boolean{
        val cm: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return when(activeNetwork?.type){
            null -> false
            ConnectivityManager.TYPE_WIFI, ConnectivityManager.TYPE_MOBILE -> true
            else -> false
        }
    }


    // added api level 21 and 23
    @RequiresApi(Build.VERSION_CODES.M)
    private fun isConnectAfterVersionM(): Boolean{
        val cm: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
        return when{
            capabilities == null -> false
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

}

data class NetworkState(var oldState: Boolean? = null, var newState: Boolean = false)

class NetCallback constructor(val changeState: (Boolean) -> Unit): ConnectivityManager.NetworkCallback() {
    override fun onAvailable(network: Network) {
        changeState(true)
        super.onAvailable(network)
    }

    override fun onLost(network: Network) {
        changeState(false)
        super.onLost(network)
    }
}

class NetChangeReceiver(val changeState: (Boolean) -> Unit): BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        val cm: ConnectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager ?: return
        val networkInfo = cm.activeNetworkInfo
        val state = when(networkInfo?.type){
            ConnectivityManager.TYPE_WIFI -> true
            ConnectivityManager.TYPE_MOBILE -> true
            else -> false
        }
        changeState(state)
    }

}