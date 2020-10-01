package com.marias.android.notes.screens.archiveNotes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.marias.android.notes.data.dto.Note
import com.marias.android.notes.data.repository.NoteRepository
import com.marias.android.notes.screens.activeNotes.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before

import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class ArchiveNotesViewModelTest {
    private val mockRepository = mock(NoteRepository::class.java)
    private lateinit var viewModel: ArchiveNotesViewModel

    @get:Rule
    val executorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

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