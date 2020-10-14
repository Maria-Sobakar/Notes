package com.marias.android.notes.screens.activeNotes


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer

import com.marias.android.notes.data.dto.Note
import com.marias.android.notes.data.repository.NoteRepository
import com.marias.android.notes.utils.Event
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*

import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

import org.mockito.Mockito.*
import java.util.*

@ExperimentalCoroutinesApi
class ActiveNotesViewModelTest {
    private val mockRepository = mock(NoteRepository::class.java)
    private lateinit var viewModel: ActiveNotesViewModel

    @get:Rule
    val executorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Before
    fun setUp() {
        viewModel = ActiveNotesViewModel(mockRepository)
    }

    @Test
    fun testGetNoteValue() {
        testCoroutineRule.runBlockingTest {
            val noteList = mutableListOf<Note>()
            `when`(mockRepository.getArchivedNotes()).thenReturn(noteList)
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

@ExperimentalCoroutinesApi
class TestCoroutineRule : TestRule {
    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)

    override fun apply(base: Statement?, description: Description?): Statement {
        return object : Statement() {
            override fun evaluate() {
                Dispatchers.setMain(testCoroutineDispatcher)

                base?.evaluate()

                Dispatchers.resetMain()
                testCoroutineDispatcher.cleanupTestCoroutines()
            }
        }
    }

    fun runBlockingTest(block: suspend TestCoroutineScope.() -> Unit) =
        testCoroutineScope.runBlockingTest { block() }
}
