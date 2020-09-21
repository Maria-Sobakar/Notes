package com.marias.android.notes.data.repository

import android.content.Context
import com.marias.android.notes.data.database.DatabaseBuilder
import com.marias.android.notes.data.dto.Note
import java.util.*


class NoteRepository private constructor(context: Context) {
    private val database = DatabaseBuilder.getInstance(context)
    private val noteDAO = database.noteDAO()

    suspend fun getActiveNotes() = noteDAO.getActiveNotes()
    suspend fun getArchivedNotes() = noteDAO.getArchivedNotes()
    suspend fun getNote(id: UUID) = noteDAO.getNote(id)
    suspend fun upsert(note: Note) = noteDAO.upsert(note)
    suspend fun deleteNote(note: Note) = noteDAO.deleteNote(note)

    companion object {
        private var INSTANCE: NoteRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = NoteRepository(context)
            }
        }

        fun get(): NoteRepository {
            return INSTANCE ?: throw IllegalStateException("CrimeRepository must be initialized")
        }
    }
}