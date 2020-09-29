package com.marias.android.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.marias.android.notes.screens.notes.NotesFragment
import com.marias.android.notes.screens.note.NoteFragment
import java.util.*

class MainActivity : AppCompatActivity(R.layout.activity_main), NotesFragment.Callback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (fragment == null) {
            val newFragment = NotesFragment.newInstance()

            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, newFragment)
                .commit()
        }
    }

    override fun onNoteSelected(id: UUID) {
        val noteFragment = NoteFragment.newInstance(id)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, noteFragment)
            .addToBackStack(null)
            .commit()
    }
}