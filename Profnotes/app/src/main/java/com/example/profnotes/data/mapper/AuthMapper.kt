package com.example.profnotes.data.mapper

import com.example.profnotes.data.model.auth.Token
import com.example.profnotes.data.remote.model.ApiToken
import javax.inject.Inject

class AuthMapper @Inject constructor() {
    fun fromApiToModel(token: ApiToken?): Token {
        return Token(
            token = token?.token ?: throw IllegalStateException("Wrong token data from api")
        )
    }
}