package com.marias.android.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.*

class MainActivity : AppCompatActivity(), NotesFragment.Callback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (fragment==null){
            val newFragment = NotesFragment.newInstance()

            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, newFragment)
                .commit()
        }

    }

    override fun onNoteSelected(id:UUID) {
        val noteFragment = NoteFragment.newInstance(id)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, noteFragment)
            .addToBackStack(null)
            .commit()
    }
}