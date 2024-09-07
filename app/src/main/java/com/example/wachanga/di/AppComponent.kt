package com.example.wachanga.di

import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        AppModule::class,
        NavigationModule::class,
    ]
)
@Singleton
interface AppComponent {

}
