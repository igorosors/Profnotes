package com.example.profnotes.data.repository

import com.example.profnotes.data.mapper.AuthMapper
import com.example.profnotes.data.mapper.ErrorMapper
import com.example.profnotes.data.model.Token
import com.example.profnotes.data.remote.ApiService
import com.example.profnotes.data.remote.params.LoginParams
import com.example.profnotes.data.remote.params.RegistrationParams
import retrofit2.HttpException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val authMapper: AuthMapper,
    private val errorMapper: ErrorMapper,
) : AuthRepository {

    override suspend fun login(phone: String, password: String): Token {
        return try {
            apiService.login(LoginParams(phone, password))
                .data
                .let { authMapper.fromApiToModel(it) }
        } catch (e: HttpException) {
            val apiError = errorMapper.fromApiWithGsonToModel(e)
            throw apiError
        }
    }

    override suspend fun registration(
        name: String,
        surname: String,
        avatar: String?,
        phone: String,
        password: String
    ): Token {
        return try {
            apiService.registration(
                RegistrationParams(
                    name = name,
                    surname = surname,
                    avatar = avatar,
                    phone = phone,
                    password = password
                )
            )
                .data
                .let { authMapper.fromApiToModel(it) }
        } catch (e: HttpException) {
            val apiError = errorMapper.fromApiWithGsonToModel(e)
            throw apiError
        }

    }
}