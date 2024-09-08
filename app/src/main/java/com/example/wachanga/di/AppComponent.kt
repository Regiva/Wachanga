package com.example.wachanga.di

import com.example.wachanga.MainActivity
import com.example.wachanga.feature.main.MainFragment
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        AppModule::class,
        NavigationModule::class,
        NotesModule::class,
    ]
)
@Singleton
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(mainFragment: MainFragment)
}
