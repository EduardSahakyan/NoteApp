package com.example.mynote.data.repository

import android.content.Context
import com.example.mynote.data.di.Component
import com.example.mynote.data.entity.Note
import kotlinx.coroutines.flow.Flow

internal class NoteRepositoryImpl(context: Context): NoteRepository {

    private val noteDao = Component.getDatabaseInstance(context).noteDAO()

    override suspend fun addNote(title: String, text: String, backgroundColor: Int){
        noteDao.insertNote(Note(Note.UNDEFINED, title,text,backgroundColor))
    }

    override suspend fun removeNote(id: Int) {
        noteDao.deleteNote(id)
    }

    override suspend fun editNote(note:Note){
        noteDao.updateNote(note)
    }

    override suspend fun getNote(id: Int): Flow<Note> {
        return noteDao.getNote(id)
    }

    override fun getNoteList(): Flow<List<Note>> {
        return noteDao.getNoteList()
    }
}