package com.marias.android.notes

import android.app.Application
import com.marias.android.notes.data.database.DatabaseBuilder
import com.marias.android.notes.data.repository.NoteRepository

class NotesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DatabaseBuilder.initialize(this)
    }
}