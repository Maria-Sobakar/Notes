package com.marias.android.notes.screens

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.marias.android.notes.data.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.mockito.Mockito

open class BaseTest {

    val mockRepository:NoteRepository = Mockito.mock(NoteRepository::class.java)

    @get:Rule
    val executorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

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
}