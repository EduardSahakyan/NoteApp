package com.example.mynote.domain.usecases

import com.example.mynote.data.entity.Note
import com.example.mynote.data.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

internal class GetNoteListUseCaseImpl(private val noteRepository: NoteRepository): GetNoteListUseCase {

    override operator fun invoke(): Flow<List<Note>> {
        return noteRepository.getNoteList()
    }
}