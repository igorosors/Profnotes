package com.example.profnotes.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Note(
    val id: String,
    val isLocal: Boolean = false,
    val isFavorite: Boolean = false,
    val title: String,
    val content: List<RichText>,
    val author: Author? = null,
    val date: Long,
    val comments: List<Comment>? = null,
) : Data, Parcelable