package com.example.mynote.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynote.data.entity.Note
import com.example.mynote.data.repository.NoteRepository
import com.example.mynote.domain.usecases.GetNoteListUseCase
import com.example.mynote.domain.usecases.GetNoteListUseCaseImpl
import com.example.mynote.domain.usecases.RemoveNoteUseCase
import com.example.mynote.domain.usecases.RemoveNoteUseCaseImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NoteListViewModel(private val repository: NoteRepository):ViewModel() {

    private val getNoteList:GetNoteListUseCase = GetNoteListUseCaseImpl(repository)
    private val removeNote: RemoveNoteUseCase = RemoveNoteUseCaseImpl(repository)

    private val _noteList = MutableLiveData<List<Note>>()
    val noteList : LiveData<List<Note>>
        get() = _noteList

    fun getNotes(){
        viewModelScope.launch(Dispatchers.IO) {
            getNoteList().collect {
                _noteList.postValue(it)
            }
        }
    }
    fun remove(note:Note){
        viewModelScope.launch(Dispatchers.IO) {
            removeNote(note.id)
        }
        getNotes()
    }
}