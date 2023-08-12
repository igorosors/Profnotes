package com.example.profnotes.data.remote.model

import com.google.gson.annotations.SerializedName

class ApiCourse(
    @SerializedName("id") val id: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("tags") val tags: List<String>?,
    @SerializedName("status") val status: String?,
    @SerializedName("plannedDate") val plannedDate: String?,
    @SerializedName("text") val textList: List<ApiRichText>?,
)