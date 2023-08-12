package com.example.profnotes.data.remote.model

import com.google.gson.annotations.SerializedName

class ApiComment(
    @SerializedName("id") val id: String?,
    @SerializedName("author") val author: ApiAuthor?,
    @SerializedName("text") val text: String?,
    @SerializedName("attachments") val attachments: List<String>?,
    @SerializedName("status") val status: String?,
)