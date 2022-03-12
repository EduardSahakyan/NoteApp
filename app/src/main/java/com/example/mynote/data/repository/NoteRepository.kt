package com.example.mynote.data.repository

import com.example.mynote.data.entity.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun addNote(title:String, text:String, backgroundColor:Int)

    suspend fun removeNote(id:Int)

    suspend fun editNote(note:Note)

    suspend fun getNote(id:Int):Flow<Note>

    fun getNoteList(): Flow<List<Note>>
}