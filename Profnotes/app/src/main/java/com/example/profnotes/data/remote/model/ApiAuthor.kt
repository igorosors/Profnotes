package com.example.profnotes.data.remote.model

import com.google.gson.annotations.SerializedName

class ApiAuthor(
    @SerializedName("id") val id: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("surname") val surname: String?,
    @SerializedName("role") val role: String?,
)