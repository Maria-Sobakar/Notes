package com.marias.android.notes.screens.notes

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.annotation.RestrictTo
import androidx.recyclerview.widget.RecyclerView
import com.marias.android.notes.R
import com.marias.android.notes.data.dto.Note
import com.marias.android.notes.data.repository.NoteRepository
import kotlinx.android.synthetic.main.item_note.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class NoteAdapter(val context: Context?, var noteList: List<Note>, val listener: Listener) :
    RecyclerView.Adapter<NoteAdapter.NoteHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_note, parent, false)

        return NoteHolder(view)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.bind(noteList[position])
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount() = noteList.size

    inner class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var note: Note

        init {
            itemView.setOnClickListener {
                (context as NotesFragment.Callback?)?.onNoteSelected(note.id)
            }
            itemView.setOnLongClickListener {
                it.isSelected = true

                true
            }
            val noteIbDelete = itemView.findViewById<ImageButton>(R.id.noteIbDelete)
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
            popup.menuInflater.inflate(R.menu.note_menu, popup.menu)
            popup.show()
            popup.setOnMenuItemClickListener {
                if (it.itemId == R.id.note_menu_delete) {
                    listener.onDeleteNoteClick(note)
                    true
                } else false
            }

        }
    }

    interface Listener {
        fun onDeleteNoteClick(note: Note)
    }
}
