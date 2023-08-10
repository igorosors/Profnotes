package com.example.profnotes.data.repository

import com.example.profnotes.data.model.Course

interface CoursesRepository {
    suspend fun getCourses(): List<Course>
}