package com.example.wachanga.feature.main

import moxy.MvpPresenter
import javax.inject.Inject

class MainPresenter @Inject constructor(

) : MvpPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        /*accountInteractor.getAccountMainBadges()
            .onEach { viewState.setAssignedNotifications(it) }
            .catch { *//*do nothing*//* }
            .launchIn(this)*/ // TODO:
    }
}

