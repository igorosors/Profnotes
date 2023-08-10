package com.example.profnotes.data.mapper

import com.example.profnotes.data.model.ApiError
import com.example.profnotes.data.remote.response.ErrorResponse
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.HttpException
import javax.inject.Inject

class ErrorMapper @Inject constructor(
    private val gson: Gson
) {
    fun fromApiWithGsonToModel(e: HttpException): ApiError {
        val body = e.response()?.errorBody()
        return ApiError(
            code = e.response()?.code() ?: 0,
            message  = gson.fromJson(body?.string(), ErrorResponse::class.java).message.orEmpty()
        )
    }

    fun fromApiToModel(e: HttpException): ApiError {
        val body = e.response()?.errorBody()
        return ApiError(
            code = e.response()?.code() ?: 0,
            message = body?.string().orEmpty()
        )
    }
}