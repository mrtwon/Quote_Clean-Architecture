package com.mrtwon.quote.di.mainScreenComponent

import com.mrtwon.quote.screen.Main.MainActivity
import dagger.Subcomponent

@MainScreenScope
@Subcomponent(modules = [MainScreenModule::class])
interface MainScreenComponent {
    fun inject(mainActivity: MainActivity)

}