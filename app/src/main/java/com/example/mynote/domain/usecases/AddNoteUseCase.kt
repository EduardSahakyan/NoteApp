package com.example.mynote.domain.usecases

import com.example.mynote.data.repository.NoteRepository

class AddNoteUseCase(private val noteRepository: NoteRepository) {

    operator fun invoke(title:String, text:String, backgroundColor:Int){
        noteRepository.addNote(title, text, backgroundColor)
    }
}