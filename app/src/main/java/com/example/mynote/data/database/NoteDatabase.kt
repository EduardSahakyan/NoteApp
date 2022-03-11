package com.example.mynote.data.database

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mynote.data.dao.NoteDao
import com.example.mynote.data.entity.Note


@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase:RoomDatabase() {

    abstract fun noteDAO(): NoteDao

}