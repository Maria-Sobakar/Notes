package com.marias.android.notes.screens.archiveNotes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.marias.android.notes.R
import com.marias.android.notes.data.dto.Note
import com.marias.android.notes.screens.activeNotes.ActiveNotesFragment


class ArchiveNotesAdapter(
    val context: Context?,
    var noteList: List<Note>,
    val listener: Listener
) : RecyclerView.Adapter<ArchiveNotesAdapter.ArchiveNotesHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArchiveNotesHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_note, parent, false)

        return ArchiveNotesHolder(view)
    }

    override fun onBindViewHolder(holder: ArchiveNotesHolder, position: Int) {
        holder.bind(noteList[position])
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount() = noteList.size

    inner class ArchiveNotesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var note: Note

        init {
            itemView.setOnClickListener {
                (context as ActiveNotesFragment.Callback?)?.onNoteSelected(note.id)
            }
            itemView.setOnLongClickListener {
                it.isSelected = true

                true
            }
            val noteIbDelete = itemView.findViewById<ImageButton>(R.id.notePopupMenu)
            noteIbDelete.setOnClickListener {
                showMenu(it, note)
            }
        }

        fun bind(note: Note) {
            this.note = note
            val notesTvTitle = itemView.findViewById<TextView>(R.id.notesTvTitle)
            val notesTvText = itemView.findViewById<TextView>(R.id.notesTvText)
            notesTvTitle.text = note.title
            notesTvText.text = note.text

        }

        fun showMenu(anchor: View?, note: Note) {
            val popup = PopupMenu(context, anchor)
            popup.menuInflater.inflate(R.menu.notes_screen_popup_menu, popup.menu)
            popup.show()
            popup.setOnMenuItemClickListener {
                if (it.itemId == R.id.notes_screen_delete_note) {
                    listener.onDeleteNoteClicked(note)
                    true
                } else false
            }

        }
    }
    interface Listener {
        fun onDeleteNoteClicked(note: Note)
    }
}
