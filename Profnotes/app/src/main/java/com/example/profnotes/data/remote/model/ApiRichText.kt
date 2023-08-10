package com.example.profnotes.data.remote.model

import com.google.gson.annotations.SerializedName

class ApiRichText(
    @SerializedName("text") val text: String?,
    @SerializedName("image") val image: String?,
)