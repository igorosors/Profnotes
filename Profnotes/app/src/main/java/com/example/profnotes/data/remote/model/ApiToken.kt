package com.example.profnotes.data.remote.model

import com.google.gson.annotations.SerializedName

class ApiToken(
    @SerializedName("token") val token: String?,
)