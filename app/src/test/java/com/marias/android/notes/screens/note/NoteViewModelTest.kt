package com.marias.android.notes.screens.note

import androidx.lifecycle.Observer
import com.marias.android.notes.data.dto.Note
import com.marias.android.notes.screens.BaseTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
class NoteViewModelTest: BaseTest()  {

    private lateinit var viewModel: NoteViewModel
    private var note = Note()

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
            viewModel.archivedState.observeForever(observer)
            viewModel.toArchive()
            verify(observer).onChanged(note.isArchived)
        }
    }

    @Test
    fun testNoteFromArchive(){
        testCoroutineRule.runBlockingTest {
            val observer = mock(Observer::class.java) as Observer<Boolean>
            viewModel.archivedState.observeForever(observer)
            viewModel.fromArchive()
            verify(observer).onChanged(note.isArchived)
        }
    }
}