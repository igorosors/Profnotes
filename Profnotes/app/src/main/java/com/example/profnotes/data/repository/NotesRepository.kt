package com.example.profnotes.data.repository

import com.example.profnotes.data.model.Note
import com.example.profnotes.data.model.RichText
import com.example.profnotes.data.remote.model.ApiNote
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    suspend fun getNotes(): List<Note>

    suspend fun getLocalNotesFlow(): Flow<List<Note>>

    suspend fun getCommunityNotesFlow(): Flow<List<Note>>

    suspend fun postNote(title: String, content: List<RichText>): Note

    suspend fun saveNote(vararg note: Note)
}