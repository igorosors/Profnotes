package com.example.profnotes.data.model.home

import com.example.profnotes.data.model.course.Course
import com.example.profnotes.data.model.note.Note

class HomeData(
    val courses: List<Course>,
    val localNotes: List<Note>,
    val communityNotes: List<Note>,
)