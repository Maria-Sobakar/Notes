package com.marias.android.notes.screens.note

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.marias.android.notes.data.repository.NoteRepository
import com.marias.android.notes.data.dto.Note
import kotlinx.coroutines.launch
import java.util.*

class NoteViewModel(val context: Context, val id: UUID) : ViewModel() {

    private val noteRepository = NoteRepository(context)
    private lateinit var note: Note
    val noteLiveData by lazy {
        val liveData = MutableLiveData<Note>()
        viewModelScope.launch {
            val note = noteRepository.getNote(id)
            archivedState.value = note.archived
            this@NoteViewModel.note = note
            liveData.value = note
        }
        return@lazy liveData
    }
    val closeLiveData = MutableLiveData<Boolean>()
    val archivedState = MutableLiveData<Boolean>()

    private fun updateNote() {
        viewModelScope.launch {
            if (note.title.isEmpty() && note.text.isEmpty()) {
                noteRepository.deleteNote(note)
            } else {
                noteRepository.upsert(note)
            }
        }
    }

    fun onTitleChanged(title: String) {
        note.title = title
        updateNote()
    }

    fun onTextChanged(text: String) {
        note.text = text
        updateNote()
    }

    fun deleteNote() {
        viewModelScope.launch {
            noteRepository.deleteNote(note)
            closeLiveData.value = true
        }
    }

    fun toArchive() {
        note.isArchived = true
        updateNote()
        archivedState.value = note.isArchived
    }

    fun fromArchive() {
        note.isArchived = false
        updateNote()
        archivedState.value = note.isArchived
    }


    fun getNoteText() = note.text

    class NoteViewModelFactory(val context: Context, val id: UUID) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return NoteViewModel(context, id) as T
        }
    }
}