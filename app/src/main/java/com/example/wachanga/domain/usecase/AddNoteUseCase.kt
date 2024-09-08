package com.example.wachanga.domain.usecase

import com.example.wachanga.domain.model.Note
import com.example.wachanga.domain.repository.NoteRepository
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(
    private val repository: NoteRepository,
) {

    operator fun invoke(note: Note): Note? {
        val insertedId = repository.insertNote(note)
        return repository.getNoteById(insertedId)
    }
}
