package com.example.mynote.data.repository

import android.app.Application
import android.content.Context
import com.example.mynote.data.database.NoteDatabase
import com.example.mynote.data.entity.Note
import kotlinx.coroutines.flow.Flow

internal class NoteRepositoryImpl(context: Context): NoteRepository {

    private val noteDao = NoteDatabase.getInstance(context).noteDAO()

    override suspend fun addNote(title: String, text: String, backgroundColor: Int){
        noteDao.insertNote(Note(Note.UNDEFINED, title,text,backgroundColor))
    }

    override suspend fun removeNote(id: Int) {
        noteDao.deleteNote(id)
    }

    override suspend fun editNote(note:Note){
        noteDao.updateNote(note)
    }

    override suspend fun getNote(id: Int): Note {
        return noteDao.getNote(id)
    }

    override suspend fun getNoteList(): Flow<List<Note>> {
        return noteDao.getNoteList()
    }
}