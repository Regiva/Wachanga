package com.example.wachanga.di

import android.content.Context
import androidx.room.Room
import com.example.wachanga.data.NoteDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context = context

    @Provides
    @Singleton
    fun provideDatabase(): NoteDatabase {
        return Room.databaseBuilder(
            provideContext(),
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME,
        ).build()
    }

}
