package com.marias.android.notes

import android.content.Context
import android.os.Bundle
import android.view.*

import android.widget.TextView

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class NotesFragment : Fragment() {

    interface Callback {
        fun onNoteSelected(id: UUID)
    }

    private lateinit var notesRecyclerView: RecyclerView
    private val notes = NoteList.notes
    private lateinit var adapter : NoteAdapter
    private var callback: Callback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = context as Callback?
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.notes_fragment, container, false)
        notesRecyclerView = view.findViewById(R.id.note_recycle_view)
        notesRecyclerView.layoutManager = LinearLayoutManager(context)



        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.add_note, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val note = Note()
        return when (item.itemId) {

            R.id.add_note -> {

                callback?.onNoteSelected(note.id)
                notes.add(note)
                return true
            }
            else -> super.onOptionsItemSelected(item)

        }

    }

    override fun onStart() {
        super.onStart()
        adapter = NoteAdapter(notes)
        notesRecyclerView.adapter = adapter
    }

    private inner class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var note: Note

        init {
            itemView.setOnClickListener {
                callback?.onNoteSelected(note.id)
            }
        }

        val textView: TextView = itemView.findViewById(R.id.note_title)


        fun bind(note: Note) {
            this.note = note
            textView.text = note.title
        }

    }

    private inner class NoteAdapter(var noteList: List<Note>) : RecyclerView.Adapter<NoteHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
            val view = layoutInflater.inflate(R.layout.item_note, parent, false)
            return NoteHolder(view)
        }

        override fun onBindViewHolder(holder: NoteHolder, position: Int) {
            holder.bind(noteList[position])
        }

        override fun getItemCount() = noteList.size


    }


    companion object {
        fun newInstance(): NotesFragment {
            return NotesFragment()
        }
    }
}