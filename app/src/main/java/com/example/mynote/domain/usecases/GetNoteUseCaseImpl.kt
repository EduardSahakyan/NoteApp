package com.example.mynote.domain.usecases

import com.example.mynote.data.entity.Note
import com.example.mynote.data.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

internal class GetNoteUseCaseImpl(private val noteRepository: NoteRepository): GetNoteUseCase {

    override suspend operator fun invoke(id:Int): Flow<Note> {
        return noteRepository.getNote(id)
    }
}