package com.example.profnotes.data.repository

import com.example.profnotes.data.model.auth.Token

interface RefreshTokenRepository {
    suspend fun refreshToken(phone: String, password: String): Token
}