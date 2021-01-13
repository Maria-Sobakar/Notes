package com.marias.android.notes.screens.activeNotes

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.marias.android.notes.R
import com.marias.android.notes.data.dto.Note

import kotlinx.android.synthetic.main.fragment_notes.*
import java.util.*

class ActiveNotesFragment : Fragment(R.layout.fragment_notes), Listener {

    private val viewModel: ActiveNotesViewModel by viewModels {
        ActiveNotesViewModel.ActiveNotesViewModelFactory(requireContext())
    }
    private lateinit var notesAdapter: ActiveNotesAdapter
    private var callback: Callback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = context as Callback?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        notesAdapter = ActiveNotesAdapter(context, mutableListOf(), this)
        setFragmentResultListener(ACTIVE_REQUEST_KEY) { _, _ ->
            viewModel.getNotes()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val orientation = activity?.resources?.configuration?.orientation
        val columnCount = if (orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 5
        val grid = GridLayoutManager(context, columnCount)
        grid.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (notesAdapter.getItemViewType(position)) {
                    TYPE_HEADER -> {
                        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                            2
                        } else 5
                    }
                    else -> 1
                }
            }
        }
        notesRecyclerView.layoutManager = grid

        viewModel.notesLiveData.observe(viewLifecycleOwner) { notes ->

            noNotesIv.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_no_note))
            noNotesTv.setText(R.string.no_active_notes)
            noNotesTv.isVisible = notes.isEmpty()
            noNotesIv.isVisible = notes.isEmpty()
            notesAdapter.itemList = notes
            notesRecyclerView.adapter = notesAdapter
        }

        viewModel.emptyScreenLiveData.observe(viewLifecycleOwner) { isEmpty ->
            noNotesTv.isVisible = isEmpty
        }

        viewModel.openNoteLiveData.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                callback?.onNoteSelected(it)
            }
        }

        addNoteFloatingActionButton.setOnClickListener {
            val note = Note()
            viewModel.upsert(note)
        }
        bottomAppBar.setNavigationOnClickListener {
            callback?.onArchiveScreenSelected()
        }
    }

    override fun onDeleteNoteClicked(note: Note) {
        viewModel.deleteNote(note)
    }

    override fun onPinNoteClicked(note: Note) {
        viewModel.pinNote(note)
    }

    companion object {
        private const val ACTIVE_REQUEST_KEY = "activeRequestKey"
        private const val TYPE_NOTE = 0
        private const val TYPE_HEADER = 1
    }

    interface Callback {
        fun onNoteSelected(id: UUID)
        fun onArchiveScreenSelected()
    }
}