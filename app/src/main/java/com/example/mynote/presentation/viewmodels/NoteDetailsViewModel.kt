package com.example.mynote.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynote.data.entity.Note
import com.example.mynote.domain.usecases.AddNoteUseCase
import com.example.mynote.domain.usecases.EditNoteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteDetailsViewModel: ViewModel() {

    lateinit var addNote: AddNoteUseCase
    lateinit var editNote: EditNoteUseCase
    val backgroundColor = MutableLiveData<Int>()

    fun add(title:String, text:String, backgroundColor:Int){
        viewModelScope.launch(Dispatchers.IO) {
            addNote(title, text, backgroundColor) }
    }

    fun edit(note: Note){
        viewModelScope.launch(Dispatchers.IO){
            editNote(note)
        }
    }


}