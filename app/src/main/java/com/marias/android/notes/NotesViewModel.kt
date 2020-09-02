package com.marias.android.notes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NotesViewModel: ViewModel() {
    private val noteRepository = NoteRepository.get()
    val notesLiveData = MutableLiveData<List<Note>>()

    init {
        viewModelScope.launch {
            val notes = noteRepository.getNotes()
            notesLiveData.value = notes
        }
    }

    fun getNotes() {
        viewModelScope.launch {
           val notes =  noteRepository.getNotes()
            notesLiveData.value = notes
        }
    }

    fun addNote(note: Note) {
        viewModelScope.launch {
            noteRepository.addNote(note)
        }
    }
}