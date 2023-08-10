package com.example.profnotes.data.model

class Course(
    val id: String,
    val title: String,
    val description: String,
    val tags: List<String>,
    val status: String,
    val plannedDate: String,
    val textList: List<RichText>,
)