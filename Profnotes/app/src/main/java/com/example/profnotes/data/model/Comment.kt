package com.example.profnotes.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Comment(
    val id: String,
    val author: Author,
    val text: String,
    val attachments: List<String>,
    val status: String,
) : Parcelable