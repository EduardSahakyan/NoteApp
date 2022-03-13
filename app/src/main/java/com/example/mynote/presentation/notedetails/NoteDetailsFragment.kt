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

    private var menu: Menu? = null

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
        this.menu = menu
        newNoteObserver()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.edit -> setEditable(true)
            R.id.save -> {
                if(binding.edTitile.text.toString().isNotBlank() && binding.edText.text.toString().isNotBlank()) {
                    viewModel.save(binding.edTitile.text.toString(), binding.edText.text.toString())
                    setEditable(false)
                }
                else
                    Toast.makeText(requireContext(), resources.getString(R.string.error_input), Toast.LENGTH_LONG).show()
            }
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
        observers()
        listeners()
    }

    private fun parseArgs() {
        requireArguments().apply {
            binding.edTitile.setText(getString(TITLE_EXTRA))
            binding.edText.setText(getString(TEXT_EXTRA))
            viewModel.initValues(getInt(ID_EXTRA), getBoolean(EDITABLE_EXTRA), getInt(COLOR_EXTRA))
        }
    }

    private fun newNoteObserver(){
        viewModel.isNewNote.observe(viewLifecycleOwner){
            menu?.findItem(R.id.edit)?.isVisible = !it
            menu?.findItem(R.id.save)?.isVisible = it
            setEditable(it)
        }
    }

    private fun observers(){
        viewModel.backgroundColor.observe(viewLifecycleOwner){
            binding.root.setBackgroundColor(it)
        }
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
            viewModel.setBackgroundColor(getBackgroundColor(R.color.purple_200))
        }
        colorTwo.setOnClickListener {
            viewModel.setBackgroundColor(getBackgroundColor(R.color.purple_500))
        }
        colorThree.setOnClickListener {
            viewModel.setBackgroundColor(getBackgroundColor(R.color.teal_200))
        }
    }

    private fun getBackgroundColor(id : Int) : Int{
        return ContextCompat.getColor(requireContext(), id)
    }

    private fun setEditable(editable:Boolean){
        binding.edTitile.isEnabled = editable
        binding.edText.isEnabled = editable
        menu?.findItem(R.id.edit)?.isVisible = !editable
        menu?.findItem(R.id.save)?.isVisible = editable
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