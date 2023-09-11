package com.example.profnotes.data.model.note

import android.os.Parcelable
import com.example.profnotes.data.model.note.Author
import kotlinx.parcelize.Parcelize

@Parcelize
class Comment(
    val id: String,
    val author: Author,
    val text: String,
    val attachments: List<String>,
    val status: String,
) : Parcelable