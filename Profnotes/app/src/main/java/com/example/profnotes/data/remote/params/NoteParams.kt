package com.example.profnotes.data.remote.params

import com.example.profnotes.data.remote.model.ApiRichText
import com.google.gson.annotations.SerializedName

class NoteParams(
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: List<ApiRichText>,
)