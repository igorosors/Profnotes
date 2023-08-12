package com.example.profnotes.data.repository

import com.example.profnotes.data.model.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    suspend fun getNotes(): List<Note>

    suspend fun getNotesFlow(): Flow<List<Note>>

    suspend fun saveNotes(notes: List<Note>)
}