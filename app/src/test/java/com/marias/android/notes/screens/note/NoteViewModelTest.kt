package com.marias.android.notes.screens.note

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.marias.android.notes.data.dto.Note
import com.marias.android.notes.data.repository.NoteRepository
import com.marias.android.notes.screens.activeNotes.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before

import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
class NoteViewModelTest {
    private val mockRepository = mock(NoteRepository::class.java)
    private lateinit var viewModel: NoteViewModel
    private var note = Note()
    @get:Rule
    val executorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()
    @Before
    fun setUp() {
        viewModel = NoteViewModel(mockRepository, note.id)
        viewModel.note = note
    }

    @Test
    fun testUpsertNote(){
        testCoroutineRule.runBlockingTest {
            viewModel.note = note
            viewModel.updateNote()
            verify(mockRepository).deleteNote(note)
            viewModel.note.title = "hello"
            viewModel.updateNote()
            verify(mockRepository).upsert(note)
        }
    }

    @Test
    fun testTitleChanged(){
        testCoroutineRule.runBlockingTest {
            val title = "bye"
            viewModel.onTitleChanged(title)
            verify(mockRepository).upsert(note)
        }
    }

    @Test
    fun testTextChanged(){
        testCoroutineRule.runBlockingTest {
            val text = "note"
            viewModel.onTextChanged(text)
            verify(mockRepository).upsert(note)
        }
    }

    @Test
    fun closeFragmentIfNoteDeleted(){
        testCoroutineRule.runBlockingTest {
            val observer = mock(Observer::class.java) as Observer<Boolean>
            viewModel.closeLiveData.observeForever(observer)
            viewModel.deleteNote()
            verify(observer).onChanged(true)
        }
    }

    @Test
    fun testNoteToArchive(){
        testCoroutineRule.runBlockingTest {
            val observer = mock(Observer::class.java) as Observer<Boolean>
            viewModel.isNoteArchivedLiveData.observeForever(observer)
            viewModel.toArchive()
            verify(observer).onChanged(note.isArchived)
        }
    }

    @Test
    fun testNoteFromArchive(){
        testCoroutineRule.runBlockingTest {
            val observer = mock(Observer::class.java) as Observer<Boolean>
            viewModel.isNoteArchivedLiveData.observeForever(observer)
            viewModel.fromArchive()
            verify(observer).onChanged(note.isArchived)
        }
    }
}