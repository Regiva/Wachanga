package com.example.wachanga

import android.app.Application
import com.example.wachanga.di.AppComponent
import com.example.wachanga.di.AppModule
import com.example.wachanga.di.DaggerAppComponent

class WachangaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initDI()
    }

    private fun initDI() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}
