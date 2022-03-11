package com.example.mynote.domain.usecases

import com.example.mynote.data.entity.Note
import kotlinx.coroutines.flow.Flow

interface GetNoteListUseCase {
    operator fun invoke(): Flow<List<Note>>
}