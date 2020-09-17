package com.marias.android.notes.screens.notes

import android.content.Context
import android.widget.PopupMenu
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.marias.android.notes.R
import com.marias.android.notes.data.repository.NoteRepository
import com.marias.android.notes.data.dto.Note
import com.marias.android.notes.screens.note.NoteViewModel
import kotlinx.coroutines.launch
import java.util.*

class NotesViewModel : ViewModel() {

    val newNoteLiveData = MutableLiveData<UUID>()
    val notesLiveData = MutableLiveData<List<Note>>()
    private val noteRepository = NoteRepository.get()

    init {
        viewModelScope.launch {
            val notes = noteRepository.getNotes()
            notesLiveData.value = notes
        }
    }

    fun getNotes() {
        viewModelScope.launch {
            val notes = noteRepository.getNotes()
            notesLiveData.value = notes
        }
    }

    fun upsert(note: Note) {
        viewModelScope.launch {
            noteRepository.upsert(note)
            newNoteLiveData.value = note.id
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteRepository.deleteNote(note)
            getNotes()
        }
    }
}