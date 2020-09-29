package com.marias.android.notes.screens.note


import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.marias.android.notes.R
import com.marias.android.notes.data.dto.toFormat
import kotlinx.android.synthetic.main.fragment_note.*
import java.util.*

class NoteFragment : Fragment(R.layout.fragment_note) {

    private val viewModel: NoteViewModel by viewModels {
        val id = arguments?.getSerializable(ARG_ID) as UUID
        NoteViewModel.NoteViewModelFactory(requireContext(), id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResult(REQUEST_KEY, Bundle())
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.noteLiveData.observe(viewLifecycleOwner) { note ->
            noteETTitle.setText(note.title)
            noteETText.setText(note.text)
            noteTVDate.text = note.date.toFormat()
        }
        viewModel.closeLiveData.observe(viewLifecycleOwner) { value ->
            if (value) {
                activity?.supportFragmentManager?.popBackStack()
            }
        }
    }

    override fun onStart() {
        super.onStart()

        noteETTitle.doOnTextChanged { text, _, _, _ ->
            viewModel.onTitleChanged(text.toString())
        }
        noteETText.doOnTextChanged { text, _, _, _ ->
            viewModel.onTextChanged(text.toString())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.delete_note, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete_note) {
            viewModel.deleteNote()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val ARG_ID = "noteId"
        private const val REQUEST_KEY = "requestKey"

        fun newInstance(id: UUID): NoteFragment {
            return NoteFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_ID, id)
                }

            }
        }
    }
}