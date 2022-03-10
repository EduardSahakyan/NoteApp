package com.example.mynote.domain.usecases

interface RemoveNoteUseCase {
    suspend operator fun invoke(id:Int)
}