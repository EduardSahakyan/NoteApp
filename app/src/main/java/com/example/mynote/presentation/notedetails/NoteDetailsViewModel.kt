package com.example.mynote.presentation.notedetails

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynote.R
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
    private val _backgroundColor = MutableLiveData<@ColorInt Int>()
    val backgroundColor : LiveData<Int> = _backgroundColor
    private val noteId = MutableLiveData<Int>()
    private val _isNewNote = MutableLiveData<Boolean>()
    val isNewNote : LiveData<Boolean> = _isNewNote

    private fun add(title:String, text:String, backgroundColor:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            addNote(title, text, backgroundColor) }
    }

    private fun edit(note: Note) {
        viewModelScope.launch(Dispatchers.IO){
            editNote(note)
        }
    }

    fun initValues(noteId: Int, isNewNote:Boolean, @ColorInt backgroundColor: Int) {
        _backgroundColor.value = backgroundColor
        this.noteId.value = noteId
        _isNewNote.value = isNewNote
        Log.d("TESTTT", "${_isNewNote.value}")
    }

    fun setBackgroundColor(@ColorInt id: Int) {
        _backgroundColor.value = id
    }

    fun save(title:String, text:String) {
        if(isNewNote.value!!)
            add(title, text, backgroundColor.value!!)
        else {
            val note = Note(noteId.value!!, title, text, backgroundColor.value!!)
            edit(note)
        }
    }
}