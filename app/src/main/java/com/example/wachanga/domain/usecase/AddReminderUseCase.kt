package com.example.wachanga.domain.usecase

import com.example.wachanga.domain.model.Note
import com.example.wachanga.domain.repository.NoteRepository
import com.example.wachanga.feature.reminder.data.ReminderManager
import javax.inject.Inject

class AddReminderUseCase @Inject constructor(
    private val reminderManager: ReminderManager,
    private val noteRepository: NoteRepository,
) {

    operator fun invoke(note: Note) {
        if (note.id != null) {
            reminderManager.stopReminder(note.id)
            reminderManager.startReminder(note.id, note.content)
            noteRepository.insertNote(note.copy(reminder = true))
        }
    }
}
