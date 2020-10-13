package com.marias.android.notes.screens.activeNotes


import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.marias.android.notes.R
import com.marias.android.notes.data.dto.HeaderItem
import com.marias.android.notes.data.dto.Item
import com.marias.android.notes.data.repository.NoteRepository
import com.marias.android.notes.data.dto.Note
import com.marias.android.notes.utils.Event
import kotlinx.coroutines.launch
import java.util.*

class ActiveNotesViewModel(val context: Context) : ViewModel() {

    val openNoteLiveData = MutableLiveData<Event<UUID>>()
    val notesLiveData = MutableLiveData<MutableList<Item>>()
    val emptyScreenLiveData = MutableLiveData<Boolean>()
    private val noteRepository = NoteRepository()

    init {
        viewModelScope.launch {
            getAllNotes()
        }
    }

    private suspend fun getAllNotes() {
        val normalNotes = noteRepository.getActiveNormalNotes()
        val pinnedNotes = noteRepository.getActivePinnedNotes()
        val noteList = mutableListOf<Item>()
        if (normalNotes.isNotEmpty()&&pinnedNotes.isNotEmpty()){
            noteList.add(0, HeaderItem(context.getString(R.string.pinned)))
            noteList.addAll(pinnedNotes)
            noteList.add(HeaderItem(context.getString(R.string.other)))
            noteList.addAll(normalNotes)
        } else{
            noteList.addAll(pinnedNotes)
            noteList.addAll(normalNotes)
        }
        normalNotes.sortByDescending { it.isPinned }
        emptyScreenLiveData.value = noteList.isEmpty()
        notesLiveData.value = noteList
    }

    private fun update(note: Note) {
        viewModelScope.launch {
            noteRepository.upsert(note)
            getAllNotes()
        }
    }

    fun getNotes() {
        viewModelScope.launch {
            getAllNotes()
        }
    }

    fun pinNote(note: Note) {
        note.isPinned = note.isPinned.not()
        update(note)


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
            getAllNotes()
        }
    }
}

class ActiveNotesViewModelFactory(val context: Context):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ActiveNotesViewModel(context) as T
    }
}

