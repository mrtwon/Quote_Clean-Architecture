package com.mrtwon.quote

import android.app.Application
import com.mrtwon.quote.manager.DependencyManager
import com.mrtwon.quote.manager.NetworkManager


class App: Application(){
    override fun onCreate() {
        NetworkManager.init(applicationContext)
        DependencyManager.init(applicationContext)
        super.onCreate()
    }
}