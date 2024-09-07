package com.example.wachanga.feature.main

import com.example.wachanga.domain.Note
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface MainView : MvpView {
    fun showNotes(notes: List<Note>)
}
