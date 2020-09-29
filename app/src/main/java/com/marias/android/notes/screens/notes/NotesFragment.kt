package com.marias.android.notes.screens.notes

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
import kotlinx.android.synthetic.main.fragment_notes.*
import java.util.*

class NotesFragment : Fragment(R.layout.fragment_notes), NoteAdapter.Listener {

    private val notesViewModel: NotesViewModel by viewModels{
        NotesViewModel.NotesViewModelFactory(requireContext())
    }
    private lateinit var adapter: NoteAdapter
    private var callback: Callback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = context as Callback?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = NoteAdapter(context, emptyList<Note>(), this)
        setFragmentResultListener(REQUEST_KEY) { _, _ ->
            notesViewModel.getNotes()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val orientation = activity?.resources?.configuration?.orientation
        val columnCount = if (orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 5
        notesRecyclerView.layoutManager = GridLayoutManager(context, columnCount)

        notesViewModel.notesLiveData.observe(viewLifecycleOwner) { notes ->
            notes.let {
                adapter.noteList = notes
                notesRecyclerView.adapter = adapter
            }
        }

        notesViewModel.openNoteLiveData.observe(viewLifecycleOwner) {
            it.getIdIfNotHandled()?.let {
                callback?.onNoteSelected(it)
            }


        }
        addNoteFloatingActionButton.setOnClickListener {
            val note = Note()
            notesViewModel.upsert(note)
        }
    }

    override fun onDeleteNoteClick(note: Note) {
        notesViewModel.deleteNote(note)
    }

    companion object {
        private const val REQUEST_KEY = "requestKey"

        fun newInstance(): NotesFragment {
            return NotesFragment()
        }
    }

    interface Callback {
        fun onNoteSelected(id: UUID)
    }
}