package com.example.profnotes.data.model.note

import android.os.Parcelable
import com.example.profnotes.data.model.Data
import com.example.profnotes.data.model.content.RichText
import kotlinx.parcelize.Parcelize

@Parcelize
data class Note(
    val id: String,
    val isLocal: Boolean = false,
    val isFavorite: Boolean = false,
    val title: String,
    val content: List<RichText>,
    val author: Author? = null,
    val date: Long,
    val comments: List<Comment>? = null,
) : Data, Parcelable