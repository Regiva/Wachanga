package com.example.wachanga.feature.detail

import com.example.wachanga.domain.model.Note
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface AddEditNoteView : MvpView {
    fun showAddScreen()
    fun showEditScreen(note: Note)
    fun showReminderOn()
    fun showReminderOff()
    fun showAlarmPermissionSnackbar()
}
