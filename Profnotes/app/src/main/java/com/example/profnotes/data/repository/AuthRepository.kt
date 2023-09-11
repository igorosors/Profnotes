package com.example.profnotes.data.repository

import com.example.profnotes.data.model.auth.Token

interface AuthRepository {

    suspend fun login(phone: String, password: String): Token

    suspend fun registration(
        name: String,
        surname: String,
        avatar: String?,
        phone: String,
        password: String
    ): Token
}