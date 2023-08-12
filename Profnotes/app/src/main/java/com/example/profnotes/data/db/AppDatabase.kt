package com.example.profnotes.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.profnotes.data.db.dao.CourseDao
import com.example.profnotes.data.db.dao.NoteDao
import com.example.profnotes.data.db.entity.CourseEntity
import com.example.profnotes.data.db.entity.NoteEntity

@Database(
    entities = [
        CourseEntity::class,
        NoteEntity::class,
    ],
    version = AppDatabase.DATABASE_VERSION
)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "app_database"
    }

    abstract fun courseDao(): CourseDao

    abstract fun noteDao(): NoteDao
}