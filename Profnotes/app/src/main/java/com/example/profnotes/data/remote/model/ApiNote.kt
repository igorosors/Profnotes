package com.example.profnotes.data.remote.model

import com.google.gson.annotations.SerializedName

class ApiNote(
    @SerializedName("id") val id: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("content") val content: List<ApiRichText>?,
    @SerializedName("author") val author: ApiAuthor?,
    @SerializedName("date") val date: String?,
    @SerializedName("comments") val comments: List<ApiComment>?,
)