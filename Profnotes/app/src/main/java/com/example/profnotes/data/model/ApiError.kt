package com.example.profnotes.data.model

class ApiError(
    val code: Int,
    override val message: String,
) : Exception()