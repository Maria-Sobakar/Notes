package com.marias.android.notes

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import database.DatabaseBuilder
import database.NoteDatabase
import java.util.*

private const val DATABASE_NAME = "Note_Database"

class NoteRepository private constructor(context: Context) {
    private val database = DatabaseBuilder.getInstance(context)
    private val noteDAO = database.noteDAO()

    suspend fun getNotes() = noteDAO.getNotes()
    suspend fun getNote(id:UUID) = noteDAO.getNote(id)
    suspend fun addNote(note:Note) = noteDAO.addNote(note)
    suspend fun updateNote(note: Note) = noteDAO.updateNote(note)

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