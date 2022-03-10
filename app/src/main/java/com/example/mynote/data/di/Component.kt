package com.example.mynote.data.di

import com.example.mynote.data.repository.NoteRepositoryImpl
import com.example.mynote.domain.usecases.AddNoteUseCase
import com.example.mynote.domain.usecases.EditNoteUseCase
import com.example.mynote.domain.usecases.GetNoteListUseCase
import com.example.mynote.domain.usecases.RemoveNoteUseCase
import com.example.mynote.presentation.fragments.NoteDetailsFragment
import com.example.mynote.presentation.fragments.NoteListFragment

class Component {
    fun injectListFragment(fragment:NoteListFragment){
        val application = fragment.requireActivity().application
        val repository = NoteRepositoryImpl(application)
        fragment.viewModel.getNoteList = GetNoteListUseCase(repository)
        fragment.viewModel.removeNote = RemoveNoteUseCase(repository)
    }
    fun injectDetailsFragment(fragment:NoteDetailsFragment){
        val application = fragment.requireActivity().application
        val repository = NoteRepositoryImpl(application)
        fragment.viewModel.addNote = AddNoteUseCase(repository)
        fragment.viewModel.editNote = EditNoteUseCase(repository)
    }
}