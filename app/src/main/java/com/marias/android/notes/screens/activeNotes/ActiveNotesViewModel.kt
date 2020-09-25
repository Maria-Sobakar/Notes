package com.marias.android.notes.screens.activeNotes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marias.android.notes.data.repository.NoteRepository
import com.marias.android.notes.data.dto.Note
import com.marias.android.notes.utils.Event
import kotlinx.coroutines.launch
import java.util.*

class ActiveNotesViewModel : ViewModel() {

    val newNoteLiveData = MutableLiveData<Event<UUID>>()
    val notesLiveData = MutableLiveData<List<Note>>()
    private val noteRepository = NoteRepository.get()

    init {
        viewModelScope.launch {
            val notes = noteRepository.getActiveNotes()
            notesLiveData.value = notes
        }
    }

    fun getNotes() {
        viewModelScope.launch {
            val notes = noteRepository.getActiveNotes()
            notesLiveData.value = notes
        }
    }

    fun upsert(note: Note) {
        viewModelScope.launch {
            noteRepository.upsert(note)
            newNoteLiveData.value = Event(note.id)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteRepository.deleteNote(note)
            getNotes()
        }
    }
}