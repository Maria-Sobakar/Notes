package com.marias.android.notes.screens.archiveNotes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.marias.android.notes.data.dto.Note
import com.marias.android.notes.data.repository.NoteRepository
import kotlinx.coroutines.launch

class ArchiveNotesViewModel(private val noteRepository: NoteRepository): ViewModel() {

    val notesLiveData = MutableLiveData<List<Note>>()

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
class ArchiveNotesViewModelFactory(private val noteRepository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ArchiveNotesViewModel(noteRepository) as T
    }
}