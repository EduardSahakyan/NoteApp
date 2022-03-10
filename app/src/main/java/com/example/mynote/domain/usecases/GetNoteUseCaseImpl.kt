package com.example.mynote.domain.usecases

import com.example.mynote.data.repository.NoteRepository

class GetNoteUseCaseImpl(private val noteRepository: NoteRepository): GetNoteUseCase {

    override suspend operator fun invoke(id:Int){
        noteRepository.getNote(id)
    }
}