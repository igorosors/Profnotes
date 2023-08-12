package com.example.profnotes.data.model

class Comment(
    val id: String,
    val author: Author,
    val text: String,
    val attachments: List<String>,
    val status: String,
)