package com.example.mynote.domain.usecases

import com.example.mynote.data.entity.Note
import kotlinx.coroutines.flow.Flow

interface GetNoteUseCase {
    suspend operator fun invoke(id:Int): Flow<Note>
}