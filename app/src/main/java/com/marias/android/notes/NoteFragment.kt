package com.marias.android.notes


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import java.util.*

private const val ARG_ID = "noteId"
private const val REQUEST_KEY = "requestKey"

class NoteFragment : Fragment() {

    private lateinit var titleEditText: EditText
    private lateinit var dateTextView: TextView
    private lateinit var noteTextField: EditText

    private val viewModel: NoteViewModel by viewModels {
        val id = arguments?.getSerializable(ARG_ID) as UUID
        NoteViewModelFactory(id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResult(REQUEST_KEY, Bundle())
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

        viewModel.noteLiveData.observe(viewLifecycleOwner) { note ->
            updateUI(note.title, note.date, note.text)
        }
    }

    private fun updateUI(title: String, date: Date, text: String) {
        titleEditText.setText(title)
        dateTextView.text = date.toString()
        noteTextField.setText(text)
    }

    override fun onStart() {
        super.onStart()

        titleEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.onTitleUpdated(text.toString())
        }
        noteTextField.doOnTextChanged { text, _, _, _ ->
            viewModel.onTextUpdated(text.toString())
        }
    }

    companion object {
        fun newInstance(id: UUID): NoteFragment {
            val arg = Bundle().apply {
                putSerializable(ARG_ID, id)
            }
            return NoteFragment().apply {
                arguments = arg
            }
        }
    }
}