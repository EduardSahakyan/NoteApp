package com.example.mynote.presentation.notedetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynote.data.entity.Note
import com.example.mynote.data.repository.NoteRepository
import com.example.mynote.domain.usecases.AddNoteUseCase
import com.example.mynote.domain.usecases.AddNoteUseCaseImpl
import com.example.mynote.domain.usecases.EditNoteUseCase
import com.example.mynote.domain.usecases.EditNoteUseCaseImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteDetailsViewModel(private val repository: NoteRepository): ViewModel() {

    private val addNote: AddNoteUseCase = AddNoteUseCaseImpl(repository)
    private val editNote: EditNoteUseCase = EditNoteUseCaseImpl(repository)
    val backgroundColor = MutableLiveData<Int>()

    fun add(title:String, text:String, backgroundColor:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            addNote(title, text, backgroundColor) }
    }

    fun edit(note: Note) {
        viewModelScope.launch(Dispatchers.IO){
            editNote(note)
        }
    }
}