package database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.marias.android.notes.Note
import java.util.*

@Dao
interface NoteDAO{
    @Query("SELECT * FROM note ")
    suspend fun getNotes(): List<Note>

    @Query("SELECT * FROM note WHERE id=(:id)")
    suspend fun getNote(id:UUID): Note

    @Update
    suspend fun updateNote (note:Note)

    @Insert
    suspend fun addNote(note: Note)


}