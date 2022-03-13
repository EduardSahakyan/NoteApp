package com.example.mynote.data.dao

import androidx.room.*
import com.example.mynote.data.entity.Note
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao {

    @Query("SELECT * FROM notes")
    fun getNoteList(): Flow<List<Note>>
    @Query("SELECT * FROM notes WHERE id =:noteId LIMIT 1 ")
    fun getNote(noteId: Int):Flow<Note>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)
    @Update
    suspend fun updateNote(note: Note)
    @Query("DELETE FROM notes WHERE id =:noteId")
    suspend fun deleteNote(noteId: Int)
}