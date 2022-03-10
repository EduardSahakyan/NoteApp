package com.example.mynote.data.dao

import androidx.room.*
import com.example.mynote.data.entity.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes")
    fun getNoteList(): List<Note>
    @Query("SELECT * FROM notes WHERE id =:noteId LIMIT 1 ")
    fun getNote(noteId: Int):Note
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: Note)
    @Update
    fun updateNote(note: Note)
    @Query("DELETE FROM notes WHERE id =:noteId")
    fun deleteNote(noteId: Int)
}