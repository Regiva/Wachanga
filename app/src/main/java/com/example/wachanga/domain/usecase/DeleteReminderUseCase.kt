package com.example.wachanga.domain.usecase

import android.util.Log
import com.example.wachanga.domain.model.Note
import com.example.wachanga.domain.repository.NoteRepository
import com.example.wachanga.feature.reminder.data.ReminderManager
import javax.inject.Inject

class DeleteReminderUseCase @Inject constructor(
    private val reminderManager: ReminderManager,
    private val noteRepository: NoteRepository,
) {

    operator fun invoke(note: Note) {
        if (note.id != null) {
            reminderManager.stopReminder(note.id)
            noteRepository.insertNote(note.copy(reminder = false))
            Log.d("rere", "use case")
        }
    }
}
