package com.example.wachanga.feature.main

import android.util.Log
import com.example.wachanga.domain.model.Note
import com.example.wachanga.domain.usecase.AddNoteUseCase
import com.example.wachanga.domain.usecase.GetNotesUseCase
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
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
        getNotes()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    private fun getNotes() {
        val disposable = getNotesUseCase()
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { items ->
                Observable.timer(1, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext {
                        viewState.hideLoading()
                    }
                    .map { items }
                    .toFlowable(BackpressureStrategy.BUFFER)
            }
            .subscribe(
                { items ->
                    if (items.isEmpty()) {
                        viewState.showEmpty()
                    } else {
                        viewState.hideEmpty()
                        val (notes, completed) = items.partition { note -> !note.done }
                        if (notes.isNotEmpty()) {
                            viewState.showNotes(notes)
                        } else {
                            viewState.hideNotes()
                        }
                        if (completed.isNotEmpty()) {
                            viewState.showCompleted(completed)
                        } else {
                            viewState.hideCompleted()
                        }
                    }
                },
                { error ->
                    Log.e(this::class.simpleName, error.message ?: "Unknown error")
                }
            )

        disposables.addAll(disposable)
    }

    fun onCheckboxClicked(note: Note) {
        val disposable = Completable.fromCallable {
            addNoteUseCase(note.copy(done = !note.done))
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

        disposables.addAll(disposable)
    }

    fun onNoteClicked(note: Note) {
        // TODO: router.navigateTo(Screens.Detail)
    }

    fun onAddClicked() {
        // TODO: add/edit note screen
        val disposable = Completable.fromCallable {
            addNoteUseCase(
                Note(
                    id = null,
                    content = "Заметка",
                    done = false,
                )
            )
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

        disposables.addAll(disposable)
    }
}

