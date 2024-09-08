package com.example.wachanga.feature.detail

import android.util.Log
import com.example.wachanga.domain.model.Note
import com.example.wachanga.domain.usecase.AddNoteUseCase
import com.example.wachanga.domain.usecase.DeleteNoteUseCase
import com.example.wachanga.domain.usecase.GetNoteByIdUseCase
import com.github.terrakok.cicerone.Router
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class AddEditNotePresenter @AssistedInject constructor(
    private val addNoteUseCase: AddNoteUseCase,
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val router: Router,
    @Assisted private val noteId: Long?,
) : MvpPresenter<AddEditNoteView>() {

    private val disposables = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        if (noteId != null) {
            getNoteById(noteId)
        } else {
            viewState.showAddScreen()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    private fun getNoteById(id: Long) {
        val disposable = getNoteByIdUseCase(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { note ->
                    viewState.showEditScreen(note)
                },
                { error ->
                    // TODO: viewState.showError() -> Toast
                    Log.e(this::class.simpleName, error.message ?: "Unknown error")
                }
            )

        disposables.addAll(disposable)
    }

    fun addReminder() {
        // TODO: reminder issue #6
    }

    fun deleteNote() {
        val disposable = Completable.fromCallable {
            deleteNoteUseCase(
                Note(id = noteId, content = "", done = false)
            )
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

        disposables.addAll(disposable)
        onBackPressed()
    }

    fun saveNote(content: String) {
        val disposable = Completable.fromCallable {
            addNoteUseCase(
                Note(id = noteId, content = content, done = false)
            )
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

        disposables.addAll(disposable)
        onBackPressed()
    }

    fun onBackPressed() {
        router.exit()
    }

    @AssistedFactory
    interface Factory {
        fun create(noteId: Long?): AddEditNotePresenter
    }
}
