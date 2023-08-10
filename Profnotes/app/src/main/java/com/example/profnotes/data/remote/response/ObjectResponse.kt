package com.example.profnotes.data.remote.response

import com.google.gson.annotations.SerializedName

class ObjectResponse<T>(
    @SerializedName("data") val data: T,
)