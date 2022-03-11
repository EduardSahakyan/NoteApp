package com.example.mynote.presentation.notelist

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
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NoteListViewModel(private val repository: NoteRepository) : ViewModel() {

    private val getNoteList: GetNoteListUseCase = GetNoteListUseCaseImpl(repository)
    private val removeNote: RemoveNoteUseCase = RemoveNoteUseCaseImpl(repository)

    private val _noteList = MutableLiveData<List<Note>>()
    val noteList: LiveData<List<Note>> = _noteList

    fun getNotes() {
        getNoteList()
            .flowOn(Dispatchers.IO)
            .onEach {
                _noteList.value = it
            }
            .flowOn(Dispatchers.Main)
            .launchIn(viewModelScope)
    }

    fun remove(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            removeNote(note.id)
        }
        getNotes()
    }
}