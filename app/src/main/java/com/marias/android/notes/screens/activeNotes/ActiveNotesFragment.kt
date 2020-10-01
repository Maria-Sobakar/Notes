package com.marias.android.notes.screens.activeNotes

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
import com.marias.android.notes.data.repository.NoteRepository
import kotlinx.android.synthetic.main.fragment_notes.*
import java.util.*

class ActiveNotesFragment : Fragment(R.layout.fragment_notes), Listener {

    private val viewModel: ActiveNotesViewModel by viewModels {
        ActiveNotesViewModelFactory(NoteRepository(requireContext()))
    }
    private lateinit var adapter: ActiveNotesAdapter

    private var callback: Callback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = context as Callback?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = ActiveNotesAdapter(context, emptyList(), this)
        setFragmentResultListener(ACTIVE_REQUEST_KEY) { _, _ ->
            viewModel.getNotes()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val orientation = activity?.resources?.configuration?.orientation
        val columnCount = if (orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 5
        notesRecyclerView.layoutManager = GridLayoutManager(context, columnCount)

        viewModel.notesLiveData.observe(viewLifecycleOwner) { notes ->
            adapter.noteList = notes
            notesRecyclerView.adapter = adapter
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

    companion object {
        private const val ACTIVE_REQUEST_KEY = "activeRequestKey"
    }

    interface Callback {
        fun onNoteSelected(id: UUID)
        fun onArchiveScreenSelected()
    }

}