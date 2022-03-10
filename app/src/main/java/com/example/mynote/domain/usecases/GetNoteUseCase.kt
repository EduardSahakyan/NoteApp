package com.example.mynote.domain.usecases

interface GetNoteUseCase {
    suspend operator fun invoke(id:Int)
}