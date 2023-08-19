package com.example.profnotes.data.repository

import com.example.profnotes.data.model.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    suspend fun getNotes(): List<Note>

    suspend fun getLocalNotesFlow(): Flow<List<Note>>

    suspend fun getCommunityNotesFlow(): Flow<List<Note>>

    suspend fun saveNote(vararg note: Note)
}