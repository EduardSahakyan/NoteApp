package com.example.mynote.domain.usecases

import com.example.mynote.data.entity.Note
import com.example.mynote.data.repository.NoteRepository

class GetNoteListUseCase(private val noteRepository: NoteRepository) {

    operator fun invoke(): List<Note>{
        return noteRepository.getNoteList()
    }
}