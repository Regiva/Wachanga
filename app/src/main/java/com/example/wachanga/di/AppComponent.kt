package com.example.wachanga.di

import com.example.wachanga.MainActivity
import com.example.wachanga.feature.detail.AddEditNoteFragment
import com.example.wachanga.feature.main.MainFragment
import com.example.wachanga.feature.reminder.di.ReminderModule
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        AppModule::class,
        NavigationModule::class,
        NotesModule::class,
        ReminderModule::class,
    ]
)
@Singleton
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(mainFragment: MainFragment)
    fun inject(addEditNoteFragment: AddEditNoteFragment)
}
