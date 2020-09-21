package com.marias.android.notes.screens.archiveNotes

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.marias.android.notes.R
import com.marias.android.notes.data.dto.Note

import com.marias.android.notes.screens.activeNotes.ActiveNotesFragment

import kotlinx.android.synthetic.main.fragment_notes.*


class ArchiveNotesFragment: Fragment(R.layout.fragment_notes), ArchiveNotesAdapter.Listener {

    private val viewModel: ArchiveNotesViewModel by viewModels()
    private lateinit var adapter: ArchiveNotesAdapter
    private var callback: ActiveNotesFragment.Callback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = context as ActiveNotesFragment.Callback?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = ArchiveNotesAdapter(context, emptyList(), this)
        setFragmentResultListener(ARCHIVE_REQUEST_KEY) { _, _ ->
            viewModel.getNotes()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomAppBar.visibility = View.GONE
        addNoteFloatingActionButton.visibility = View.GONE
        val orientation = activity?.resources?.configuration?.orientation
        val columnCount = if (orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 5
        notesRecyclerView.layoutManager = GridLayoutManager(context, columnCount)

        viewModel.notesLiveData.observe(viewLifecycleOwner) { notes ->
            notes.let {
                adapter.noteList = notes
                notesRecyclerView.adapter = adapter
            }
        }
    }

    override fun onDeleteNoteClicked(note: Note) {
        viewModel.deleteNote(note)
    }

    companion object {
        private const val ARCHIVE_REQUEST_KEY = "archiveRequestKey"

        fun newInstance(): ArchiveNotesFragment {
            return ArchiveNotesFragment()
        }
    }
}