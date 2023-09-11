package com.example.profnotes.data.repository

import com.example.profnotes.data.model.note.Note
import com.example.profnotes.data.model.content.RichText
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    suspend fun getNotes(): List<Note>

    suspend fun getLocalNotesFlow(): Flow<List<Note>>

    suspend fun getCommunityNotesFlow(): Flow<List<Note>>

    suspend fun getFavouriteLocalNotesFlow(): Flow<List<Note>>

    suspend fun getFavouriteCommunityNotesFlow(): Flow<List<Note>>

    suspend fun postNote(title: String, content: List<RichText>): Note

    suspend fun saveNote(vararg note: Note)

    suspend fun searchLocalNotes(text: String): Flow<List<Note>>

    suspend fun searchCommunityNotes(text: String): Flow<List<Note>>

    suspend fun searchFavouritesLocalNotes(text: String): Flow<List<Note>>

    suspend fun searchFavouritesCommunityNotes(text: String): Flow<List<Note>>
}