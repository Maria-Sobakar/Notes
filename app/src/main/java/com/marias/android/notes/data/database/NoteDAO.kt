package com.marias.android.notes.data.database

import androidx.room.*
import com.marias.android.notes.data.dto.Note
import java.util.*

@Dao
interface NoteDAO {
    @Query("SELECT * FROM note WHERE archived = 0 and isPinned = 0 ORDER BY date DESC ")
    suspend fun getActiveNormalNotes(): MutableList<Note>

    @Query("SELECT * FROM note WHERE archived = 0 and isPinned = 1 ORDER BY date DESC ")
    suspend fun getActivePinnedNotes(): MutableList<Note>

    @Query("SELECT * FROM note WHERE archived = 1  ORDER BY date DESC ")
    suspend fun getArchivedNotes(): List<Note>

    @Query("SELECT * FROM note WHERE id=(:id)")
    suspend fun getNote(id: UUID): Note

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note): Long

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Transaction
    suspend fun upsert(note: Note) {
        val id = insert(note)
        if (id == -1L) update(note)
    }

}