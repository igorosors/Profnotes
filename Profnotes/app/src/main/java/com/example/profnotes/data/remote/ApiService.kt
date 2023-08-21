package com.example.profnotes.data.remote

import com.example.profnotes.data.remote.model.ApiNote
import com.example.profnotes.data.remote.model.ApiCourse
import com.example.profnotes.data.remote.model.ApiToken
import com.example.profnotes.data.remote.params.LoginParams
import com.example.profnotes.data.remote.params.NoteParams
import com.example.profnotes.data.remote.params.RegistrationParams
import com.example.profnotes.data.remote.response.ObjectResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("auth")
    suspend fun login(@Body params: LoginParams): ObjectResponse<ApiToken>

    @GET("courses")
    suspend fun getCourses(): ObjectResponse<List<ApiCourse>>

    @GET("community_notes")
    suspend fun getNotes(): ObjectResponse<List<ApiNote>>

    @POST("community_notes")
    suspend fun postNote(@Body params: NoteParams): ObjectResponse<ApiNote>
}