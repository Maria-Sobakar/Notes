package database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.marias.android.notes.Note

@Database (entities = [Note::class], version = 1)
@TypeConverters(TypeConverter::class)
abstract class NoteDatabase:RoomDatabase() {
    abstract fun noteDAO(): NoteDAO
}