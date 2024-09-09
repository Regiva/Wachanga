package com.example.wachanga.domain.repository

import com.example.wachanga.domain.model.Note
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable

interface NoteRepository {

    fun getNotes(): Flowable<List<Note>>

    fun getNoteById(id: Long): Observable<Note>

    fun insertNote(note: Note)

    fun deleteNote(note: Note)
}
