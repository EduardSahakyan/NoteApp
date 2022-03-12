package com.example.mynote.presentation.notelist

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.mynote.R
import com.example.mynote.data.di.Component
import com.example.mynote.data.entity.Note
import com.example.mynote.data.repository.NoteRepository
import com.example.mynote.databinding.FragmentNoteListBinding
import com.example.mynote.presentation.notedetails.NoteDetailsFragment
import com.example.mynote.presentation.notelist.adapter.NoteAdapter
import java.lang.RuntimeException

class NoteListFragment : Fragment() {

    private val noteAdapter: NoteAdapter by lazy {
        NoteAdapter()
    }
    private val repository: NoteRepository by lazy {
        Component.getRepository(requireContext())
    }
    private val viewModelFactory: NoteListViewModelFactory by lazy {
        NoteListViewModelFactory(repository)
    }
    private val viewModel : NoteListViewModel by viewModels { viewModelFactory }

    private var _binding : FragmentNoteListBinding? = null
    private val binding : FragmentNoteListBinding
    get() = _binding ?: throw RuntimeException("NoteListBinding is null")

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
        init()
        observers()
        listeners()
    }

    private fun init(){
        binding.recyclerView.adapter = noteAdapter
        viewModel.getNotes()
    }

    private fun observers(){
        viewModel.noteList.observe(viewLifecycleOwner){
            noteAdapter.updateData(it)
        }
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
            val note = Note(0, "", "", ContextCompat.getColor(requireActivity(), R.color.purple_200))
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