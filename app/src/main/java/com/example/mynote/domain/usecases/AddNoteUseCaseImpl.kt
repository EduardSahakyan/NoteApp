package com.example.mynote.domain.usecases

import com.example.mynote.data.repository.NoteRepository

internal class AddNoteUseCaseImpl(private val noteRepository: NoteRepository) : AddNoteUseCase{

    override suspend fun invoke(title:String, text:String, backgroundColor:Int){
        noteRepository.addNote(title, text, backgroundColor)
    }
}