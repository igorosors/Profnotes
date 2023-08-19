package com.example.profnotes.data.remote

import com.example.profnotes.data.remote.model.ApiToken
import com.example.profnotes.data.remote.params.LoginParams
import com.example.profnotes.data.remote.params.RegistrationParams
import com.example.profnotes.data.remote.response.ObjectResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface RefreshApiService {
    @POST("auth")
    suspend fun login(@Body params: LoginParams): ObjectResponse<ApiToken>

    @POST("register")
    suspend fun registration(@Body params: RegistrationParams): ObjectResponse<ApiToken>
}