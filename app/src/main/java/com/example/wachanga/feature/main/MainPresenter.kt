package com.example.wachanga.feature.main

import com.example.wachanga.domain.usecase.GetNotesUseCase
import moxy.MvpPresenter
import javax.inject.Inject

class MainPresenter @Inject constructor(
    getNotesUseCase: GetNotesUseCase,
) : MvpPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        /*accountInteractor.getAccountMainBadges()
            .onEach { viewState.setAssignedNotifications(it) }
            .catch { *//*do nothing*//* }
            .launchIn(this)*/ // TODO:
    }
}

