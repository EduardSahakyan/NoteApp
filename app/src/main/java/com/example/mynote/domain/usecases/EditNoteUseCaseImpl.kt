package com.example.mynote.domain.usecases

import com.example.mynote.data.entity.Note
import com.example.mynote.data.repository.NoteRepository

internal class EditNoteUseCaseImpl(private val noteRepository: NoteRepository): EditNoteUseCase {

    override suspend operator fun invoke(note:Note){
        noteRepository.editNote(note)
    }
}