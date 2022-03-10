package com.example.mynote.domain.usecases

import com.example.mynote.data.entity.Note

interface EditNoteUseCase {
    suspend operator fun invoke(note: Note)
}