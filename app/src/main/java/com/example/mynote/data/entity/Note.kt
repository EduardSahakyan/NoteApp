package com.example.mynote.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note (
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    @ColumnInfo(name = "title")
    var title:String,
    @ColumnInfo(name = "text")
    var text:String,
    @ColumnInfo(name = "backgroundColor")
    var backgroundColor:Int
){
    companion object{
        const val UNDEFINED = 0
    }
}