package com.example.wachanga.di

import com.example.wachanga.data.NoteDatabase
import com.example.wachanga.data.repository.NoteRepositoryImpl
import com.example.wachanga.domain.repository.NoteRepository
import dagger.Module
import dagger.Provides

@Module
class NotesModule {

    @Provides
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

}
