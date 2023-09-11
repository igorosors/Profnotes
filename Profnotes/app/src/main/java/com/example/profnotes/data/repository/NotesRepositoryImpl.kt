package com.example.profnotes.data.repository

import com.example.profnotes.data.db.AppDatabase
import com.example.profnotes.data.mapper.ErrorMapper
import com.example.profnotes.data.mapper.NoteMapper
import com.example.profnotes.data.model.note.Note
import com.example.profnotes.data.model.content.RichText
import com.example.profnotes.data.remote.ApiService
import com.example.profnotes.data.remote.params.NoteParams
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

    override suspend fun getLocalNotesFlow(): Flow<List<Note>> {
        return database.noteDao().getLocalNotesFlow().map { notes ->
            notes.map { noteMapper.fromEntityToModel(it) }
        }
    }

    override suspend fun getCommunityNotesFlow(): Flow<List<Note>> {
        return database.noteDao().getCommunityNotesFlow().map { notes ->
            notes.map { noteMapper.fromEntityToModel(it) }
        }
    }

    override suspend fun getFavouriteLocalNotesFlow(): Flow<List<Note>> {
        return database.noteDao().getFavouritesLocalNotesFlow().map { notes ->
            notes.map { noteMapper.fromEntityToModel(it) }
        }
    }

    override suspend fun getFavouriteCommunityNotesFlow(): Flow<List<Note>> {
        return database.noteDao().getFavouritesCommunityNotesFlow().map { notes ->
            notes.map { noteMapper.fromEntityToModel(it) }
        }
    }

    override suspend fun postNote(title: String, content: List<RichText>): Note {
        val note = apiService.postNote(
            NoteParams(
                title = title,
                content = content.map { noteMapper.fromModelToApi(it) },
            )
        ).data
        return noteMapper.fromApiToModel(note)
    }

    override suspend fun saveNote(vararg note: Note) {
        database.noteDao().saveNote(*note.map { noteMapper.fromModelToEntity(it) }.toTypedArray())
    }

    override suspend fun searchLocalNotes(text: String): Flow<List<Note>> {
        return database.noteDao().searchLocalNotes(text).map { notes ->
            notes.map { noteMapper.fromEntityToModel(it) }
        }
    }

    override suspend fun searchCommunityNotes(text: String): Flow<List<Note>> {
        return database.noteDao().searchCommunityNotes(text).map { notes ->
            notes.map { noteMapper.fromEntityToModel(it) }
        }
    }

    override suspend fun searchFavouritesLocalNotes(text: String): Flow<List<Note>> {
        return database.noteDao().searchFavouritesLocalNotes(text).map { notes ->
            notes.map { noteMapper.fromEntityToModel(it) }
        }
    }

    override suspend fun searchFavouritesCommunityNotes(text: String): Flow<List<Note>> {
        return database.noteDao().searchFavouritesCommunityNotes(text).map { notes ->
            notes.map { noteMapper.fromEntityToModel(it) }
        }
    }
}