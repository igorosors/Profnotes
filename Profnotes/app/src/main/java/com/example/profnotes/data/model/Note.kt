package com.example.profnotes.data.model

class Note(
    val id: String,
    val isLocal: Boolean = false,
    val isFavorite: Boolean = false,
    val title: String,
    val content: List<RichText>,
    val author: Author? = null,
    val date: String,
    val comments: List<Comment>? = null,
)