package com.example.mynote.data.di

import android.content.Context
import androidx.room.Room
import com.example.mynote.data.database.NoteDatabase
import com.example.mynote.data.repository.NoteRepository
import com.example.mynote.data.repository.NoteRepositoryImpl

object Component {

    private var INSTANCE : NoteDatabase? = null
    private val LOCK = Any()

    fun getRepository(context: Context): NoteRepository{
        return NoteRepositoryImpl(context)
    }

    fun getDatabaseInstance(context: Context): NoteDatabase {
        INSTANCE?.let {
            return it
        }
        synchronized(LOCK){
            INSTANCE?.let {
                return it
            }
            val db = Room.databaseBuilder(context, NoteDatabase::class.java, "notes")
                .build()
            INSTANCE = db
            return db
        }
    }

}