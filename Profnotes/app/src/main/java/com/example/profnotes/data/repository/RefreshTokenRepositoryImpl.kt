package com.example.profnotes.data.repository

import android.util.Log
import com.example.profnotes.data.mapper.AuthMapper
import com.example.profnotes.data.model.Token
import com.example.profnotes.data.remote.RefreshApiService
import com.example.profnotes.data.remote.params.LoginParams
import javax.inject.Inject

class RefreshTokenRepositoryImpl @Inject constructor(
    private val apiService: RefreshApiService,
    private val authMapper: AuthMapper,
) : RefreshTokenRepository {
    override suspend fun refreshToken(phone: String, password: String): Token {
        return apiService.login(LoginParams(phone, password))
            .data
            .let { authMapper.fromApiToModel(it) }
    }
}