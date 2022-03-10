package com.example.mynote.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynote.data.entity.Note
import com.example.mynote.domain.usecases.GetNoteListUseCase
import com.example.mynote.domain.usecases.RemoveNoteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteListViewModel:ViewModel() {

    lateinit var getNoteList: GetNoteListUseCase
    lateinit var removeNote: RemoveNoteUseCase

    private val _noteList = MutableLiveData<List<Note>>()
    val noteList : LiveData<List<Note>>
        get() = _noteList

    fun getNotes(){
        viewModelScope.launch(Dispatchers.IO) {
            _noteList.postValue(getNoteList())
        }
    }
    fun remove(note:Note){
        viewModelScope.launch(Dispatchers.IO) {
            removeNote(note.id)
        }
        getNotes()
    }
}