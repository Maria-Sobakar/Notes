package com.marias.android.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.marias.android.notes.screens.activeNotes.ActiveNotesFragment
import com.marias.android.notes.screens.archiveNotes.ArchiveNotesFragment
import com.marias.android.notes.screens.note.NoteFragment
import java.util.*

class MainActivity : AppCompatActivity(R.layout.activity_main), ActiveNotesFragment.Callback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (fragment == null) {
            val newFragment = ActiveNotesFragment.newInstance()

            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, newFragment)
                .commit()
        }
    }

    override fun onNoteSelected(id: UUID) {
        val fragment = NoteFragment.newInstance(id)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onArchiveScreenSelected() {
        val fragment = ArchiveNotesFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}