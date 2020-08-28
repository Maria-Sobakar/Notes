package com.marias.android.notes


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.util.*

private const val ARG_ID = "noteId"

class NoteFragment : Fragment() {
    private val notes = NoteList.notes
    private lateinit var note: Note
    private lateinit var titleEditText: EditText
    private lateinit var dateTextView: TextView
    private lateinit var noteTextField: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        notes.forEach{
            val id = arguments?.getSerializable(ARG_ID) as UUID
            if (it.id ==id){
                note = it
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.note_fragment, container, false)

        titleEditText = view.findViewById(R.id.note_title) as EditText
        dateTextView = view.findViewById(R.id.note_date) as TextView
        noteTextField = view.findViewById(R.id.note_text) as EditText

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleEditText.setText(note.title)
        dateTextView.text = note.date.toString()
        noteTextField.setText(note.text)

    }

    override fun onStart() {
        super.onStart()
        val titleChangeListener = object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                note.title = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {

            }

        }
        val textChangeListener = object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                note.text = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {

            }

        }
        titleEditText.addTextChangedListener(titleChangeListener)
        noteTextField.addTextChangedListener(textChangeListener)


    }


    companion object {
        fun newInstance(id:UUID): NoteFragment{
            val arg = Bundle().apply {
                putSerializable(ARG_ID, id)
            }
            return NoteFragment().apply {
                arguments = arg
            }
        }
    }
}