package com.marias.android.notes.screens.activeNotes


import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.marias.android.notes.data.repository.NoteRepository
import com.marias.android.notes.data.dto.Note
import com.marias.android.notes.utils.Event
import kotlinx.coroutines.launch
import java.util.*

class ActiveNotesViewModel(private val noteRepository: NoteRepository) : ViewModel() {

    val openNoteLiveData = MutableLiveData<Event<UUID>>()
    val notesLiveData = MutableLiveData<List<Note>>()

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
            openNoteLiveData.value = Event(note.id)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteRepository.deleteNote(note)
            getNotes()
        }
    }
}
class ActiveNotesViewModelFactory(val noteRepository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ActiveNotesViewModel(noteRepository) as T
    }
}
