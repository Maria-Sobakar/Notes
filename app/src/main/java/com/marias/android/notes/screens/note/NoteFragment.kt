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
import androidx.navigation.Navigation
import com.marias.android.notes.R
import com.marias.android.notes.data.dto.toFormat
import kotlinx.android.synthetic.main.fragment_note.*
import java.util.*

class NoteFragment : Fragment(R.layout.fragment_note) {

    private lateinit var toArchiveItem: MenuItem
    private lateinit var fromArchiveItem: MenuItem

    private val viewModel: NoteViewModel by viewModels {
        val id = arguments?.getSerializable(ARG_ID) as UUID
        NoteViewModel.NoteViewModelFactory(requireContext(), id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResult(ACTIVE_REQUEST_KEY, Bundle())
        setFragmentResult(ARCHIVE_REQUEST_KEY, Bundle())
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
               val navController = Navigation.findNavController(requireActivity(), R.id.navigation_host_fragment)
                navController.popBackStack()
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
        inflater.inflate(R.menu.note_screen_menu, menu)

        toArchiveItem = menu.findItem(R.id.note_screen_to_archive)
        fromArchiveItem = menu.findItem(R.id.note_screen_from_archive)

        viewModel.isNoteArchivedLiveData.observe(viewLifecycleOwner) { value ->
            if (value) {
                toArchiveItem.isVisible = false
                fromArchiveItem.isVisible = true
            } else {
                toArchiveItem.isVisible = true
                fromArchiveItem.isVisible = false
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.note_screen_delete_note -> {
                viewModel.deleteNote()
                true
            }
            R.id.note_screen_to_archive -> {
                viewModel.toArchive()
                toArchiveItem.isVisible = false
                fromArchiveItem.isVisible = true
                true
            }
            R.id.note_screen_from_archive -> {
                viewModel.fromArchive()
                toArchiveItem.isVisible = true
                fromArchiveItem.isVisible = false
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val ARG_ID = "noteId"
        private const val ACTIVE_REQUEST_KEY = "activeRequestKey"
        private const val ARCHIVE_REQUEST_KEY = "archiveRequestKey"
    }
}