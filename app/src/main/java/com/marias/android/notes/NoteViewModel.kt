package com.marias.android.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class NoteViewModel(val id: UUID) : ViewModel() {

    private val noteRepository = NoteRepository.get()
    private lateinit var note: Note
    val noteLiveData by lazy {
        val liveData = MutableLiveData<Note>()
        viewModelScope.launch {
            val note = noteRepository.getNote(id)
            this@NoteViewModel.note = note
            liveData.value = note
        }
        return@lazy liveData
    }

    private fun updateNote() {
        viewModelScope.launch {
            noteRepository.updateNote(note)
        }
    }

    fun onTitleUpdated(title: String) {
        note.title = title
        updateNote()
    }

    fun onTextUpdated(text: String) {
        note.text = text
        updateNote()
    }
}