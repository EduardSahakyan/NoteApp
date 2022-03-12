package com.example.mynote.presentation.notelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mynote.data.repository.NoteRepository

class NoteListViewModelFactory(val repository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(NoteRepository::class.java).newInstance(repository)
    }
}