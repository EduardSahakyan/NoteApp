package com.example.mynote.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mynote.data.dao.NoteDao
import com.example.mynote.data.entity.Note


@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase:RoomDatabase() {

    abstract fun noteDAO(): NoteDao

    companion object{

        private var INSTANCE : NoteDatabase? = null
        private val LOCK = Any()

        fun getInstance(application: Application): NoteDatabase{
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK){
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(application, NoteDatabase::class.java, "notes")
                    .build()
                INSTANCE = db
                return db
            }
        }

    }
}