package com.example.profnotes.data.model

class Note(
    val id: String,
    val title: String,
    val content: List<RichText>,
    val author: Author,
    val date: String,
    val comments: List<Comment>,
)