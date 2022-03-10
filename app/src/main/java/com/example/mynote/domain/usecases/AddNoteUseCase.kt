package com.example.mynote.domain.usecases

interface AddNoteUseCase {
    suspend operator fun invoke(title:String, text:String, backgroundColor:Int)
}