package com.marias.android.notes.screens.archiveNotes

import androidx.lifecycle.Observer
import com.marias.android.notes.data.dto.Note
import com.marias.android.notes.screens.BaseTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before

import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class ArchiveNotesViewModelTest: BaseTest() {

    private lateinit var viewModel: ArchiveNotesViewModel

    @Before
    fun setUp() {
        viewModel = ArchiveNotesViewModel(mockRepository)
    }

    @Test
    fun testGetNoteValue() {
        testCoroutineRule.runBlockingTest {
            val note = Note(isArchived = true)
            val noteList = mutableListOf(note)
            `when`(mockRepository.getArchivedNotes()).thenReturn(noteList)
            val observer = mock(Observer::class.java) as Observer<List<Note>>
            viewModel.notesLiveData.observeForever(observer)
            viewModel.getNotes()
            verify(observer).onChanged(noteList)
        }
    }
}