package com.example.profnotes.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.profnotes.data.db.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    companion object {
        const val NOTE_TABLE = "notes"
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNote(vararg noteEntity: NoteEntity)

    @Query("select * from $NOTE_TABLE")
    fun getNotesFlow(): Flow<List<NoteEntity>>
}