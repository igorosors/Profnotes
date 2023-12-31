package com.example.profnotes.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.profnotes.data.db.dao.NoteDao

@Entity(tableName = NoteDao.NOTE_TABLE)
data class NoteEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "isLocal") val isLocal: Int,
    @ColumnInfo(name = "isFavorite") val isFavorite: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "date") val date: Long,
    @ColumnInfo(name = "comments") val comments: String,
)