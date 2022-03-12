package com.example.mynote.domain.usecases

import com.example.mynote.data.repository.NoteRepository

internal class RemoveNoteUseCaseImpl(private val noteRepository: NoteRepository): RemoveNoteUseCase {

    override suspend operator fun invoke(id:Int){
        noteRepository.removeNote(id)
    }
}