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

    @Query("select * from $NOTE_TABLE where isLocal = 1")
    fun getLocalNotesFlow(): Flow<List<NoteEntity>>

    @Query("select * from $NOTE_TABLE where isLocal = 0")
    fun getCommunityNotesFlow(): Flow<List<NoteEntity>>

    @Query("select * from $NOTE_TABLE where title like '%' || :text || '%' and isLocal = 1")
    fun searchLocalNotes(text: String): Flow<List<NoteEntity>>

    @Query("select * from $NOTE_TABLE where title like '%' || :text || '%' and isLocal = 0")
    fun searchCommunityNotes(text: String): Flow<List<NoteEntity>>

    @Query("select * from $NOTE_TABLE where isLocal = 1 and isFavorite = 1")
    fun getFavouritesLocalNotesFlow(): Flow<List<NoteEntity>>

    @Query("select * from $NOTE_TABLE where isLocal = 0 and isFavorite = 1")
    fun getFavouritesCommunityNotesFlow(): Flow<List<NoteEntity>>

    @Query("select * from $NOTE_TABLE where title like '%' || :text || '%' and isLocal = 1 and isFavorite = 1")
    fun searchFavouritesLocalNotes(text: String): Flow<List<NoteEntity>>

    @Query("select * from $NOTE_TABLE where title like '%' || :text || '%' and isLocal = 0 and isFavorite = 1")
    fun searchFavouritesCommunityNotes(text: String): Flow<List<NoteEntity>>
}