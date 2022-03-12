package com.example.mynote.presentation.notedetails

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.mynote.R
import com.example.mynote.data.di.Component
import com.example.mynote.data.entity.Note
import com.example.mynote.data.repository.NoteRepository
import com.example.mynote.databinding.FragmentNoteDetailsBinding
import java.lang.RuntimeException

class NoteDetailsFragment : Fragment() {

    private var isNewNote: Boolean = false
    @ColorInt private var colorBackground: Int = -1
    private var noteId: Int = -1;
    private var edit: MenuItem? = null
    private var save: MenuItem? = null

    private val repository: NoteRepository by lazy {
        Component.getRepository(requireContext())
    }
    private val viewModelFactory: NoteDetailsViewModelFactory by lazy {
        NoteDetailsViewModelFactory(repository)
    }
    private val viewModel: NoteDetailsViewModel by viewModels{ viewModelFactory }

    private var _binding:FragmentNoteDetailsBinding? = null
    private val binding:FragmentNoteDetailsBinding
    get() = _binding ?: throw RuntimeException("NoteDetailBinding is null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.action_menu, menu)
        edit = menu.findItem(R.id.edit)
        edit?.isVisible = !isNewNote
        save = menu.findItem(R.id.save)
        save?.isVisible = isNewNote
        setEditable(isNewNote)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.edit -> setEditable(true)
            R.id.save -> save(isNewNote)
        }
        return false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseArgs()
        listeners()
    }

    private fun parseArgs() {
        requireArguments().apply {
            binding.edTitile.setText(getString(TITLE_EXTRA))
            binding.edText.setText(getString(TEXT_EXTRA))
            isNewNote = getBoolean(EDITABLE_EXTRA)
            noteId = getInt(ID_EXTRA)
            colorBackground = getInt(COLOR_EXTRA)
            binding.root.setBackgroundColor(colorBackground)
        }
    }

    private fun save(isNoteNew:Boolean) {
        if(binding.edTitile.text.toString().isNotBlank() && binding.edText.text.toString().isNotBlank()) {
            if(isNoteNew)
                viewModel.add(binding.edTitile.text.toString(), binding.edText.text.toString(), colorBackground)
            else {
                val note = Note(noteId, binding.edTitile.text.toString(), binding.edText.text.toString(), colorBackground)
                viewModel.edit(note)
            }
            requireActivity().supportFragmentManager.popBackStack()
        }
        else
            Toast.makeText(requireContext(), "Please enter title/text", Toast.LENGTH_LONG).show()
    }

    private fun listeners() = with(binding){
        color.setOnClickListener {
            if(card.isVisible)
                card.visibility = View.GONE
            else{
                card.visibility = View.VISIBLE
            }
            setEditable(true)
        }
        colorOne.setOnClickListener {
            setBackgroundColor(R.color.purple_200)
        }
        colorTwo.setOnClickListener {
            setBackgroundColor(R.color.purple_500)
        }
        colorThree.setOnClickListener {
            setBackgroundColor(R.color.teal_200)
        }
    }

    private fun setBackgroundColor(id : Int){
        colorBackground = ContextCompat.getColor(requireContext(), id)
        binding.root.setBackgroundColor(colorBackground)
    }

    private fun setEditable(editable:Boolean){
        binding.edTitile.isEnabled = editable
        binding.edText.isEnabled = editable
        edit?.isVisible = !editable
        save?.isVisible = editable
    }

    companion object {

        private const val TITLE_EXTRA = "title"
        private const val COLOR_EXTRA = "color"
        private const val ID_EXTRA = "id"
        private const val TEXT_EXTRA = "text"
        private const val EDITABLE_EXTRA = "flag"

        fun newInstance(note:Note, editable:Boolean) : NoteDetailsFragment {
            return NoteDetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(COLOR_EXTRA, note.backgroundColor)
                    putInt(ID_EXTRA, note.id)
                    putString(TITLE_EXTRA, note.title)
                    putString(TEXT_EXTRA, note.text)
                    putBoolean(EDITABLE_EXTRA, editable)
                }
            }
        }
    }
}