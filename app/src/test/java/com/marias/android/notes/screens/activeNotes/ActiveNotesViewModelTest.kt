package com.marias.android.notes.screens.activeNotes


import androidx.lifecycle.Observer

import com.marias.android.notes.data.dto.Note
import com.marias.android.notes.screens.BaseTest
import com.marias.android.notes.utils.Event
import kotlinx.coroutines.*

import org.junit.*

import org.mockito.Mockito.*
import java.util.*

@ExperimentalCoroutinesApi
class ActiveNotesViewModelTest: BaseTest() {

    private lateinit var viewModel: ActiveNotesViewModel

    @Before
    fun setUp() {
        viewModel = ActiveNotesViewModel(mockRepository)
    }

    @Test
    fun testGetNoteValueEmptyList() {
        testCoroutineRule.runBlockingTest {
            val noteList = mutableListOf<Note>()
            `when`(mockRepository.getActiveNotes()).thenReturn(noteList)
            val observer = mock(Observer::class.java) as Observer<List<Note>>
            viewModel.notesLiveData.observeForever(observer)
            viewModel.getNotes()
            verify(observer).onChanged(noteList)
        }
    }
    @Test
    fun testGetNoteValueWithElement() {
        testCoroutineRule.runBlockingTest {
            val noteList = mutableListOf<Note>()
            noteList.add(Note())
            `when`(mockRepository.getActiveNotes()).thenReturn(noteList)
            val observer = mock(Observer::class.java) as Observer<List<Note>>
            viewModel.notesLiveData.observeForever(observer)
            viewModel.getNotes()
            verify(observer).onChanged(noteList)
        }
    }

    @Test
    fun testUpsertNoteValue() {
        testCoroutineRule.runBlockingTest {
            val note = Note()
            val observer = mock(Observer::class.java) as Observer<Event<UUID>>
            viewModel.openNoteLiveData.observeForever(observer)
            viewModel.upsert(note)
            verify(observer).onChanged(any())
        }
    }
}


