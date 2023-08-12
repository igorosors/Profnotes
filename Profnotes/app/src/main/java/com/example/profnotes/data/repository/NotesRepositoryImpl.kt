package com.example.profnotes.data.repository

import com.example.profnotes.data.db.AppDatabase
import com.example.profnotes.data.mapper.ErrorMapper
import com.example.profnotes.data.mapper.NoteMapper
import com.example.profnotes.data.model.Note
import com.example.profnotes.data.remote.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val noteMapper: NoteMapper,
    private val errorMapper: ErrorMapper,
    private val database: AppDatabase
) : NotesRepository {

    override suspend fun getNotes(): List<Note> {
        return try {
            apiService.getNotes()
                .data
                .map { noteMapper.fromApiToModel(it) }
        } catch (e: HttpException) {
            val apiError = errorMapper.fromApiToModel(e)
            throw apiError
        }
    }

    override suspend fun getNotesFlow(): Flow<List<Note>> {
        return database.noteDao().getNotesFlow().map { notes ->
            notes.map { noteMapper.fromEntityToModel(it) }
        }
    }

    override suspend fun saveNotes(notes: List<Note>) {
        database.noteDao().saveNote(*notes.map { noteMapper.fromModelToEntity(it) }.toTypedArray())
    }

}