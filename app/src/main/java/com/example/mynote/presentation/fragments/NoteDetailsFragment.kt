package com.example.mynote.presentation.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mynote.R
import com.example.mynote.data.di.Component
import com.example.mynote.data.entity.Note
import com.example.mynote.databinding.FragmentNoteDetailsBinding
import com.example.mynote.presentation.viewmodels.NoteDetailsViewModel
import com.example.mynote.presentation.viewmodels.NoteListViewModel
import java.lang.RuntimeException

class NoteDetailsFragment : Fragment() {

    private var editable: Boolean = false
    @ColorInt private var colorBackground: Int = -1
    private var noteId: Int = -1;
    private var _binding:FragmentNoteDetailsBinding? = null
    private val binding:FragmentNoteDetailsBinding
    get() = _binding ?: throw RuntimeException("NoteDetailBinding is null")
    lateinit var viewModel: NoteDetailsViewModel
    private lateinit var edit: MenuItem
    private lateinit var save: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.action_menu, menu)
        edit = menu.findItem(R.id.edit)
        edit.isVisible = !editable
        save = menu.findItem(R.id.save)
        save.isVisible = editable
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.edit ->{
                setEditable(binding.edTitile, true)
                setEditable(binding.edText, true)
                edit.isVisible = false
                save.isVisible = true;

            }
            R.id.save ->{
                if(editable){
                    if(binding.edTitile.text.toString().isNotBlank() && binding.edText.text.toString().isNotBlank()){
                        viewModel.add(binding.edTitile.text.toString(),
                            binding.edText.text.toString(),
                            colorBackground)
                        requireActivity().supportFragmentManager.popBackStack()
                        edit.isVisible = true
                        save.isVisible = false;
                    }
                    else
                        Toast.makeText(requireContext(), "Please enter title/text", Toast.LENGTH_LONG).show()
                }
                else{
                    if(binding.edTitile.text.toString().isNotBlank() && binding.edText.text.toString().isNotBlank()){
                        val note = Note(noteId,
                            binding.edTitile.text.toString(),
                            binding.edText.text.toString(),
                            colorBackground)
                        viewModel.edit(note)
                        requireActivity().supportFragmentManager.popBackStack()
                        edit.isVisible = true
                        save.isVisible = false;
                        Log.d("TESTT", "$noteId")
                    }
                    else
                        Toast.makeText(requireContext(), "Please enter title/text", Toast.LENGTH_LONG).show()
                }
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
        viewModel = ViewModelProvider(this)[NoteDetailsViewModel::class.java]
        Component().injectDetailsFragment(this)
        requireArguments().getString(TITLE_EXTRA)?.let {
            binding.edTitile.setText(it)
        }
        requireArguments().getString(TEXT_EXTRA)?.let {
            binding.edText.setText(it)
        }
        requireArguments().getBoolean(EDITABLE_EXTRA).let {
            editable = it
        }
        requireArguments().getInt(ID_EXTRA).let {
            noteId = it
        }
        requireArguments().getInt(COLOR_EXTRA).let {
            colorBackground = if(editable)
                ContextCompat.getColor(requireActivity(), R.color.purple_200)
            else
                it
            binding.root.setBackgroundColor(colorBackground)
        }
        setEditable(binding.edTitile, editable)
        setEditable(binding.edText, editable)

        binding.apply {
            color.setOnClickListener {
                if(card.isVisible)
                    card.visibility = View.GONE
                else{
                    edit.isVisible = false
                    save.isVisible = true;
                    card.visibility = View.VISIBLE
                }
            }
            colorOne.setOnClickListener {
                colorBackground = ContextCompat.getColor(requireContext(), R.color.purple_200)
                binding.root.setBackgroundColor(colorBackground)
            }
            colorTwo.setOnClickListener {
                colorBackground = ContextCompat.getColor(requireContext(), R.color.purple_500)
                binding.root.setBackgroundColor(colorBackground)
            }
            colorThree.setOnClickListener {
                colorBackground = ContextCompat.getColor(requireContext(), R.color.teal_200)
                binding.root.setBackgroundColor(colorBackground)
            }
        }
    }


    private fun setEditable(editText: EditText, editable:Boolean){
        editText.isEnabled = editable
    }

    companion object{

        private const val TITLE_EXTRA = "title"
        private const val COLOR_EXTRA = "color"
        private const val ID_EXTRA = "id"
        private const val TEXT_EXTRA = "text"
        const val EDITABLE_EXTRA = "flag"

        fun newInstance(note:Note, editable:Boolean) : NoteDetailsFragment{
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