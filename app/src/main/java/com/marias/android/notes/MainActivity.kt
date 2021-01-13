package com.marias.android.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.marias.android.notes.screens.activeNotes.ActiveNotesFragment
import com.marias.android.notes.screens.archiveNotes.ArchiveNotesFragment
import com.marias.android.notes.screens.note.NoteFragment
import com.marias.android.notes.screens.notification.NotificationFragment
import java.util.*

class MainActivity : AppCompatActivity(R.layout.activity_main), ActiveNotesFragment.Callback, NoteFragment.MenuCallback {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navController = Navigation.findNavController(this, R.id.navigation_host_fragment)
    }

    override fun onNoteSelected(id: UUID) {
        val arg = Bundle().apply {
            putSerializable(NoteFragment.ARG_ID, id)
        }
        navController.navigate(R.id.noteFragment, arg)
    }

    override fun onArchiveScreenSelected() {
        navController.navigate(R.id.archiveNotesFragment)
    }

    override fun onCreateNotificationSelected(notificationText:String) {
        val arg = Bundle().apply {
            putString(NotificationFragment.ARG_TEXT_ID, notificationText)
        }
        navController.navigate(R.id.notificationFragment, arg)
    }
}