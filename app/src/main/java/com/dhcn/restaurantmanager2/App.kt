package com.dhcn.restaurantmanager2

import android.app.Application
import com.dhcn.restaurantmanager2.dependencyinjection.AppModule

class App : Application() {
    companion object {
        private lateinit var sInstant: App
            private set

        fun getInstant() = sInstant
    }

    lateinit var appModule: AppModule
    override fun onCreate() {
        super.onCreate()
        sInstant = this
        appModule = AppModule(this)
    }

}