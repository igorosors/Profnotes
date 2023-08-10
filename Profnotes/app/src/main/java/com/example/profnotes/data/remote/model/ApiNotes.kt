package com.example.profnotes.data.remote.model

import com.google.gson.annotations.SerializedName

class ApiNotes(
    @SerializedName("id") val id: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("content") val content: List<ApiRichText>?,
    @SerializedName("author") val author: List<Author>,
    @SerializedName("date") val date: Long?,
)