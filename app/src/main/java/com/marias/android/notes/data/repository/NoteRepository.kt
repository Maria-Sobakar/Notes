package com.marias.android.notes.data.repository

import android.content.Context
import com.marias.android.notes.data.database.DatabaseBuilder
import com.marias.android.notes.data.dto.Note
import java.util.*


class NoteRepository constructor(context: Context) {
    private val database = DatabaseBuilder.instance
    private val noteDAO = database.noteDAO()

    suspend fun getNotes() = noteDAO.getNotes()
    suspend fun getNote(id: UUID) = noteDAO.getNote(id)
    suspend fun upsert(note: Note) = noteDAO.upsert(note)
    suspend fun deleteNote(note: Note) = noteDAO.deleteNote(note)

//    companion object {
//        private var INSTANCE = NoteRepository(context)
//        fun get(): NoteRepository {
//            return INSTANCE ?: throw IllegalStateException("CrimeRepository must be initialized")
//        }
//    }
}