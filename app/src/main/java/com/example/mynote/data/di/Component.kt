package com.example.mynote.data.di

import android.content.Context
import com.example.mynote.data.repository.NoteRepository
import com.example.mynote.data.repository.NoteRepositoryImpl
import com.example.mynote.domain.usecases.AddNoteUseCaseImpl
import com.example.mynote.domain.usecases.EditNoteUseCaseImpl
import com.example.mynote.domain.usecases.GetNoteListUseCaseImpl
import com.example.mynote.domain.usecases.RemoveNoteUseCaseImpl
import com.example.mynote.presentation.fragments.NoteDetailsFragment
import com.example.mynote.presentation.fragments.NoteListFragment

object Component {


    fun getRepository(context: Context): NoteRepository{
        return NoteRepositoryImpl(context)
    }

}