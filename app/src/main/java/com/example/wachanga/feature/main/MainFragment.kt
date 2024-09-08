package com.example.wachanga.feature.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wachanga.R
import com.example.wachanga.WachangaApplication
import com.example.wachanga.databinding.FragmentMainBinding
import com.example.wachanga.domain.model.Note
import com.example.wachanga.feature.main.adapter.ItemAnimator
import com.example.wachanga.feature.main.adapter.NotesAdapter
import com.example.wachanga.feature.main.adapter.SpacingItemDecoration
import dagger.Lazy
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject

class MainFragment : MvpAppCompatFragment(), MainView {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var presenterProvider: Lazy<MainPresenter>

    private val presenter by moxyPresenter { presenterProvider.get() }

    private val notesAdapter by lazy {
        NotesAdapter(
            onItemClickedListener = presenter::onNoteClicked,
            onCheckboxClickedListener = presenter::onCheckboxClicked,
        )
    }

    private val completedAdapter by lazy {
        NotesAdapter(
            onItemClickedListener = presenter::onNoteClicked,
            onCheckboxClickedListener = presenter::onCheckboxClicked,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (requireActivity().application as WachangaApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNotesRecycler()
        initCompletedRecycler()
        setOnClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun showNotes(notes: List<Note>) {
        binding.rvNotes.isVisible = true
        notesAdapter.submitItems(notes)
    }

    override fun hideNotes() {
        binding.rvNotes.isVisible = false
    }

    override fun showCompleted(notes: List<Note>) {
        binding.completedGroup.isVisible = true
        completedAdapter.submitItems(notes)
    }

    override fun hideCompleted() {
        binding.completedGroup.isVisible = false
    }

    override fun hideAllContent() {
        binding.allContentGroup.isVisible = false
    }

    override fun showEmpty() {
        binding.tvEmpty.isVisible = true
    }

    override fun hideEmpty() {
        binding.tvEmpty.isVisible = false
    }

    override fun showLoading() {
        binding.loading.isVisible = true
    }

    override fun hideLoading() {
        binding.loading.isVisible = false
    }

    private fun initNotesRecycler() {
        binding.rvNotes.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvNotes.adapter = notesAdapter
        binding.rvNotes.addItemDecoration(
            SpacingItemDecoration(
                betweenItems = resources.getDimensionPixelSize(R.dimen.space_16),
                paddingTop = resources.getDimensionPixelSize(R.dimen.space_32),
                orientation = RecyclerView.VERTICAL,
            ),
        )
        binding.rvNotes.itemAnimator = ItemAnimator()
    }

    private fun initCompletedRecycler() {
        binding.rvCompleted.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvCompleted.adapter = completedAdapter
        binding.rvCompleted.addItemDecoration(
            SpacingItemDecoration(
                betweenItems = resources.getDimensionPixelSize(R.dimen.space_16),
                paddingTop = resources.getDimensionPixelSize(R.dimen.space_16),
                orientation = RecyclerView.VERTICAL,
            ),
        )
        binding.rvCompleted.itemAnimator = ItemAnimator()
    }

    private fun setOnClickListeners() {
        binding.flbAdd.setOnClickListener {
            presenter.onAddClicked()
        }
        binding.ivAccordion.setOnClickListener {
            binding.rvCompleted.isVisible = !binding.rvCompleted.isVisible
        }
        binding.tvAccordion.setOnClickListener {
            binding.rvCompleted.isVisible = !binding.rvCompleted.isVisible
        }
    }

}
