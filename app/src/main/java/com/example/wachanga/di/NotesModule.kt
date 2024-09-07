package com.example.wachanga.di

import com.example.wachanga.data.repository.NoteRepositoryImpl
import com.example.wachanga.domain.repository.NoteRepository
import dagger.Binds
import dagger.Module

@Module
interface NotesModule {

    @Binds
    fun provideNoteRepository(impl: NoteRepositoryImpl): NoteRepository

}
