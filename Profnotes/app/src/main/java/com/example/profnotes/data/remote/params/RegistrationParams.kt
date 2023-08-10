package com.example.profnotes.data.remote.params

import com.google.gson.annotations.SerializedName

class RegistrationParams(
    @SerializedName("name") val name: String,
    @SerializedName("surname") val surname: String,
    @SerializedName("avatar") val avatar: String?,
    @SerializedName("phone") val phone: String,
    @SerializedName("passwordHashed") val password: String,
)