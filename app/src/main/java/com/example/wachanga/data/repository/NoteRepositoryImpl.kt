package com.example.wachanga.data.repository

import com.example.wachanga.data.datasource.NoteDao
import com.example.wachanga.domain.model.Note
import com.example.wachanga.domain.repository.NoteRepository
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val dao: NoteDao,
) : NoteRepository {

    override fun getNotes(): Flowable<List<Note>> {
        return dao.getNotes()
    }

    override fun getNoteById(id: Long): Observable<Note> {
        return dao.getNoteById(id)
    }

    override fun insertNote(note: Note) {
        dao.insertNote(note)
    }

    override fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }

}
