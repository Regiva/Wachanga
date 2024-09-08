package com.example.wachanga

import android.app.Application
import com.example.wachanga.di.AppComponent
import com.example.wachanga.di.AppModule
import com.example.wachanga.di.DaggerAppComponent
import com.example.wachanga.di.NavigationModule
import com.example.wachanga.di.NotesModule

class WachangaApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initDI()
    }

    private fun initDI() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .navigationModule(NavigationModule())
            .notesModule(NotesModule())
            .build()
    }
}
