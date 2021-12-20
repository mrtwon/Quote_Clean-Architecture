package com.mrtwon.quote.manager

import android.content.Context
import com.mrtwon.quote.di.appComponent.AppComponent
import com.mrtwon.quote.di.appComponent.DaggerAppComponent
import com.mrtwon.quote.di.mainScreenComponent.MainScreenComponent
import javax.inject.Singleton

object DependencyManager {
    fun init(context: Context){
        appComponent = DaggerAppComponent
            .builder()
            .addContext(context)
            .build()
    }

    fun plusMainScreenComponent(): MainScreenComponent {
        if(mainScreenComponent == null){
            mainScreenComponent = appComponent.mainScreenComponent()
        }
        return mainScreenComponent!!
    }

    fun cleanMainScreenComponent(){
        mainScreenComponent = null
    }

    private lateinit var appComponent: AppComponent
    private var mainScreenComponent: MainScreenComponent? = null

}