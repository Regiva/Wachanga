package com.example.wachanga.domain.usecase

import com.example.wachanga.domain.model.Note
import com.example.wachanga.domain.repository.NoteRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class GetNoteByIdUseCase @Inject constructor(
    private val repository: NoteRepository,
) {

    operator fun invoke(noteId: Long): Observable<Note> {
        return repository.getNoteById(noteId)
    }
}
