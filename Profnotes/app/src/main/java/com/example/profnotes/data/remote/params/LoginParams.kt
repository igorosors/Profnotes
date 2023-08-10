package com.example.profnotes.data.remote.params

import com.google.gson.annotations.SerializedName

class LoginParams(
    @SerializedName("phone") val phone: String,
    @SerializedName("passwordHashed") val password: String,
)