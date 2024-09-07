package com.example.wachanga.domain.usecase

import com.example.wachanga.domain.model.Note
import com.example.wachanga.domain.repository.NoteRepository
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
    private val repository: NoteRepository,
) {

    operator fun invoke(): Flowable<List<Note>> {
        return repository.getNotes()
    }

}
