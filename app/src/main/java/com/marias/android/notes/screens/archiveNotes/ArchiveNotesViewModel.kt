package com.marias.android.notes.screens.archiveNotes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marias.android.notes.data.dto.Note
import com.marias.android.notes.data.repository.NoteRepository
import kotlinx.coroutines.launch

class ArchiveNotesViewModel : ViewModel() {

    val notesLiveData = MutableLiveData<List<Note>>()
    private val noteRepository = NoteRepository.get()

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