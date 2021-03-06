package com.marias.android.notes.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.marias.android.notes.data.database.converters.DateTypeConverter
import com.marias.android.notes.data.database.converters.UUIDTypeConverter
import com.marias.android.notes.data.dto.Note

@Database (entities = [Note::class], version = 1)
@TypeConverters(DateTypeConverter::class, UUIDTypeConverter::class)
abstract class NoteDatabase:RoomDatabase() {
    abstract fun noteDAO(): NoteDAO
}
