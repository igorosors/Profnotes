package com.example.profnotes.data.repository

import com.example.profnotes.data.remote.model.ApiCourse

interface NotesRepository {
    suspend fun getCommunityNotes(): List<ApiCourse>
}