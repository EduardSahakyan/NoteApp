package com.example.mynote.data.repository

import android.app.Application
import com.example.mynote.data.database.NoteDatabase
import com.example.mynote.data.entity.Note

class NoteRepositoryImpl(application: Application): NoteRepository {

    private val noteDao = NoteDatabase.getInstance(application).noteDAO()

    override fun addNote(title: String, text: String, backgroundColor: Int){
        noteDao.insertNote(Note(Note.UNDEFINED, title,text,backgroundColor))
    }

    override fun removeNote(id: Int) {
        noteDao.deleteNote(id)
    }

    override fun editNote(note:Note){
        noteDao.updateNote(note)
    }

    override fun getNote(id: Int): Note {
        return noteDao.getNote(id)
    }

    override fun getNoteList(): List<Note> {
        return noteDao.getNoteList()
    }
}