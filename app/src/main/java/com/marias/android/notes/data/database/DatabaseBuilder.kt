package com.marias.android.notes.data.database

import android.content.Context
import androidx.room.Room


object DatabaseBuilder {
    private const val DATABASE_NAME = "note-database"
    lateinit var instance: NoteDatabase

    fun initialize(context: Context) {
        synchronized(NoteDatabase::class.java) {
            instance = buildRoomDB(context)
        }
    }

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            NoteDatabase::class.java,
            DATABASE_NAME
        ).addMigrations(migration_1_2)
            .build()

}