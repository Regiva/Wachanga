package com.example.wachanga.domain.repository

import com.example.wachanga.domain.model.Note
import io.reactivex.rxjava3.core.Flowable

interface NoteRepository {

    fun getNotes(): Flowable<List<Note>>

    fun getNoteById(id: Int): Note?

    fun insertNote(note: Note): Long

    fun deleteNote(note: Note)
}
