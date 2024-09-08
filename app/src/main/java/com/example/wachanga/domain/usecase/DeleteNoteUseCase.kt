package com.example.wachanga.domain.usecase

import com.example.wachanga.domain.model.Note
import com.example.wachanga.domain.repository.NoteRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository,
) {

    operator fun invoke(note: Note) {
        noteRepository.deleteNote(note)
    }
}
