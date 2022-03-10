package com.example.mynote.domain.usecases

import com.example.mynote.data.repository.NoteRepository

class RemoveNoteUseCase(private val noteRepository: NoteRepository) {

    operator fun invoke(id:Int){
        noteRepository.removeNote(id)
    }
}