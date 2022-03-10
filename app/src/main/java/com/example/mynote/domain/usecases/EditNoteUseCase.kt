package com.example.mynote.domain.usecases

import com.example.mynote.data.entity.Note
import com.example.mynote.data.repository.NoteRepository

class EditNoteUseCase(private val noteRepository: NoteRepository) {

    operator fun invoke(note:Note){
        noteRepository.editNote(note)
    }
}