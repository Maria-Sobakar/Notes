package com.marias.android.notes.screens.archiveNotes

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.marias.android.notes.data.dto.Note
import com.marias.android.notes.data.repository.NoteRepository
import com.marias.android.notes.screens.activeNotes.ActiveNotesViewModel
import kotlinx.coroutines.launch

class ArchiveNotesViewModel(context: Context): ViewModel() {

    val notesLiveData = MutableLiveData<List<Note>>()
    private val noteRepository = NoteRepository(context)

    init {
        viewModelScope.launch {
            val notes = noteRepository.getArchivedNotes()
            notesLiveData.value = notes
        }
    }

    fun getNotes() {
        viewModelScope.launch {
            val notes = noteRepository.getArchivedNotes()
            notesLiveData.value = notes
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteRepository.deleteNote(note)
            getNotes()
        }
    }
}
class ArchiveNotesViewModelFactory(val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ArchiveNotesViewModel(context) as T
    }
}