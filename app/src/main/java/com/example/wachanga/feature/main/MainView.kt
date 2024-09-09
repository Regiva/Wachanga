package com.example.wachanga.feature.main

import com.example.wachanga.domain.model.Note
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface MainView : MvpView {
    @StateStrategyType(AddToEndStrategy::class)
    fun showNotes(notes: List<Note>)
    fun hideNotes()
    @StateStrategyType(AddToEndStrategy::class)
    fun showCompleted(notes: List<Note>)
    fun hideCompleted()
    fun hideAllContent()
    fun showEmpty()
    fun hideEmpty()
    fun showLoading()
    fun hideLoading()
}
