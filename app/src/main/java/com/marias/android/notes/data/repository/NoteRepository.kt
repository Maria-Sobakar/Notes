package com.marias.android.notes.data.repository

import android.content.Context
import com.marias.android.notes.data.database.DatabaseBuilder
import com.marias.android.notes.data.dto.Note
import java.util.*

class NoteRepository  {
    private val database = DatabaseBuilder.instance
    private val noteDAO = database.noteDAO()

    suspend fun getActiveNotes() = noteDAO.getActiveNotes()
    suspend fun getArchivedNotes() = noteDAO.getArchivedNotes()
    suspend fun getNote(id: UUID) = noteDAO.getNote(id)
    suspend fun upsert(note: Note) = noteDAO.upsert(note)
    suspend fun deleteNote(note: Note) = noteDAO.deleteNote(note)
}