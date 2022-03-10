package com.example.mynote.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mynote.R
import com.example.mynote.data.di.Component
import com.example.mynote.data.entity.Note
import com.example.mynote.data.repository.NoteRepository
import com.example.mynote.data.repository.NoteRepositoryImpl
import com.example.mynote.databinding.FragmentNoteListBinding
import com.example.mynote.presentation.recyclerview.NoteAdapter
import com.example.mynote.presentation.viewmodels.NoteListViewModel
import com.example.mynote.presentation.viewmodels.NoteListViewModelFactory
import java.lang.RuntimeException

class NoteListFragment : Fragment() {

    private lateinit var noteAdapter: NoteAdapter
    private lateinit var viewModel : NoteListViewModel
    private lateinit var repository: NoteRepository


    private var _binding : FragmentNoteListBinding? = null
    private val binding : FragmentNoteListBinding
        get() = _binding ?: throw RuntimeException("NoteListBinding is null")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repository = Component.getRepository(requireContext())
        val viewModelFactory = NoteListViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[NoteListViewModel::class.java]
        noteAdapter = NoteAdapter()
        binding.recyclerView.adapter = noteAdapter
        viewModel.getNotes()
        viewModel.noteList.observe(viewLifecycleOwner){
            noteAdapter.noteList = it
            noteAdapter.notifyDataSetChanged()
        }
        listeners()
    }

    private fun listeners(){
        noteAdapter.onClickListener = {
            Log.d("TESTT", "124231")
            openDetailsFragment(it, false)
        }
        noteAdapter.onLongClickListener = { note: Note, view: View ->
            val popup = PopupMenu(requireContext(),view)
            popup.menuInflater.inflate(R.menu.popup_menu, popup.menu)
            popup.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.remove -> viewModel.remove(note)
                }
                true
            }
            popup.show()
        }
        binding.addButton.setOnClickListener{
            val note = Note(0, "", "", 1)
            openDetailsFragment(note, true)
        }
    }

    private fun openDetailsFragment(note:Note, editable:Boolean){
        val detailsFragment = NoteDetailsFragment.newInstance(note, editable)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, detailsFragment)
            .addToBackStack(null)
            .commit()
    }

}