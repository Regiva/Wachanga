package com.example.wachanga.feature.detail

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.wachanga.R
import com.example.wachanga.WachangaApplication
import com.example.wachanga.databinding.FragmentAddEditNoteBinding
import com.example.wachanga.domain.model.Note
import com.example.wachanga.util.argument
import com.google.android.material.snackbar.Snackbar
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject

class AddEditNoteFragment : MvpAppCompatFragment(), AddEditNoteView {

    private val noteId by argument<Long?>(ARG_NOTE_ID)

    private var _binding: FragmentAddEditNoteBinding? = null
    private val binding get() = _binding!!

    private val noteContent: String
        get() = binding.etNote.text.toString()

    @Inject
    lateinit var presenterFactory: AddEditNotePresenter.Factory

    private val presenter by moxyPresenter { presenterFactory.create(noteId) }

    override fun onCreate(savedInstanceState: Bundle?) {
        (requireActivity().application as WachangaApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()
        initToolbar()
    }

    private fun setOnClickListeners() {
        binding.flbSave.setOnClickListener {
            presenter.saveNote(noteContent)
        }
    }

    private fun initToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            presenter.onBackPressed()
        }
        binding.actionDelete.setOnClickListener {
            presenter.deleteNote()
        }
    }

    override fun showAddScreen() {
        binding.toolbar.setTitle(R.string.add_note_screen_toolbar)
        binding.actionReminder.isVisible = false
        binding.actionDelete.isVisible = false
    }

    override fun showEditScreen(note: Note) {
        binding.toolbar.setTitle(R.string.edit_note_screen_toolbar)
        binding.actionReminder.isVisible = true
        binding.actionDelete.isVisible = true
        binding.etNote.setText(note.content)
    }

    override fun showReminderOn() {
        with(binding.actionReminder) {
            setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_bell_on))
            setOnClickListener {
                Log.d("rere", "ON -> DELETE")
                presenter.deleteReminder(noteContent)
            }
        }
    }

    override fun showReminderOff() {
        with(binding.actionReminder) {
            setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_bell_off))
            setOnClickListener {
                Log.d("rere", "OFF -> ADD")
                presenter.addReminder(noteContent)
            }
        }
    }

    // TODO: не изменяется иконка колокольчика
    // TODO: по нажатию на уведомление не переходит на главный экран - done

    @RequiresApi(Build.VERSION_CODES.S)
    override fun showAlarmPermissionSnackbar() {
        // TODO: extract strings
        Snackbar.make(requireView(), "Нужно разрешение для точных уведомлений", Snackbar.LENGTH_LONG)
            .setAction("Настройки") {
                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                requireContext().startActivity(intent)
            }
            .show()
    }

    companion object {
        private const val ARG_NOTE_ID = "arg_note_id"

        fun create(noteId: Long? = null) : AddEditNoteFragment {
            return AddEditNoteFragment().apply {
                arguments = Bundle().apply {
                    noteId?.let { putLong(ARG_NOTE_ID, it) }
                }
            }
        }
    }
}
