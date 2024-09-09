package com.example.wachanga.feature.main

import android.util.Log
import com.example.wachanga.domain.model.Note
import com.example.wachanga.domain.usecase.AddNoteUseCase
import com.example.wachanga.domain.usecase.GetNotesUseCase
import com.example.wachanga.navigation.Screens
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val addNoteUseCase: AddNoteUseCase,
    private val router: Router,
) : MvpPresenter<MainView>() {

    private val disposables = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showLoading()
        hideLoadingWithDelay()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    fun hideLoadingWithDelay() {
        val disposable = Completable.timer(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                viewState.hideLoading()
                getNotes()
            }

        disposables.add(disposable)
    }

    fun getNotes() {
        val disposable = getNotesUseCase()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { items ->
                    if (items.isEmpty()) {
                        viewState.showEmpty()
                    } else {
                        viewState.hideEmpty()
                        val (notes, completed) = items.partition { note -> !note.done }
                        if (notes.isNotEmpty()) {
                            viewState.showNotes(notes.sortedByDescending { it.id })
                        } else {
                            viewState.hideNotes()
                        }
                        if (completed.isNotEmpty()) {
                            viewState.showCompleted(completed.sortedByDescending { it.id })
                        } else {
                            viewState.hideCompleted()
                        }
                    }
                },
                { error ->
                    // TODO: viewState.showError() -> Toast
                    Log.e(this::class.simpleName, error.message ?: "Unknown error")
                }
            )

        disposables.add(disposable)
    }

    fun onCheckboxClicked(note: Note) {
        val disposable = Completable.fromCallable {
            addNoteUseCase(note.copy(done = !note.done))
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

        disposables.add(disposable)
    }

    fun onNoteClicked(note: Note) {
        router.navigateTo(Screens.Detail(note.id))
    }

    fun onAddClicked() {
        router.navigateTo(Screens.Detail())
    }
}

