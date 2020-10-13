package com.marias.android.notes.screens.activeNotes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.marias.android.notes.R
import com.marias.android.notes.data.dto.HeaderItem
import com.marias.android.notes.data.dto.Item
import com.marias.android.notes.data.dto.Note

private const val TYPE_NOTE = 0
private const val TYPE_HEADER = 1
class ActiveNotesAdapter(
    val context: Context?,
    var itemList: MutableList<Item>,
    val listener: Listener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_NOTE) {
            val inflater = LayoutInflater.from(context)
            val view = inflater.inflate(R.layout.item_note, parent, false)
            ActiveNotesHolder(view)
        } else{
            val inflater = LayoutInflater.from(context)
            val view = inflater.inflate(R.layout.item_header, parent, false)
            HeaderViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ActiveNotesHolder->holder.bind((itemList[position]) as Note)
            is HeaderViewHolder -> holder.bind((itemList[position])as HeaderItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
       return if (itemList[position] is Note) {
            TYPE_NOTE
        } else {
            TYPE_HEADER
        }
    }

    override fun getItemCount() = itemList.size

    inner class ActiveNotesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var note: Note

        init {
            itemView.setOnClickListener {
                (context as ActiveNotesFragment.Callback?)?.onNoteSelected(note.id)
            }
            val notePopupMenuIb = itemView.findViewById<ImageButton>(R.id.notePopupMenu)
            notePopupMenuIb.setOnClickListener {
                showMenu(it, note, itemView)
            }
        }

        fun bind(note: Note) {
            this.note = note
            val notesTvTitle = itemView.findViewById<TextView>(R.id.notesTvTitle)
            val notesTvText = itemView.findViewById<TextView>(R.id.notesTvText)
            notesTvTitle.text = note.title
            notesTvText.text = note.text
        }

        private fun showMenu(anchor: View?, note: Note, itemView: View) {
            val popup = PopupMenu(context, anchor)
            popup.menuInflater.inflate(R.menu.notes_screen_popup_menu, popup.menu)
            popup.show()
            val pin = popup.menu.findItem(R.id.note_screen_pin_note)
            if (note.isPinned) {
                pin.setTitle(R.string.unpin)
            } else {
                pin.setTitle(R.string.pin)
            }
            popup.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.notes_screen_delete_note -> {
                        listener.onDeleteNoteClicked(note)
                        true
                    }
                    R.id.note_screen_pin_note -> {
                        listener.onPinNoteClicked(note)
                        if (note.isPinned) {
                            pin.setTitle(R.string.unpin)
                        } else {
                            pin.setTitle(R.string.pin)
                        }
                        true
                    }
                    else -> false
                }
            }
        }
    }

    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(header: HeaderItem) {
            val text = itemView.findViewById<TextView>(R.id.headerTV)
            text.text = header.text
        }
    }
}

interface Listener {
    fun onDeleteNoteClicked(note: Note)
    fun onPinNoteClicked(note: Note)
}

