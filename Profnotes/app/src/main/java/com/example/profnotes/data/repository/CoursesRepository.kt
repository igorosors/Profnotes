package com.example.profnotes.data.repository

import com.example.profnotes.data.model.Course
import com.example.profnotes.data.model.Note
import kotlinx.coroutines.flow.Flow

interface CoursesRepository {

    suspend fun getCourses(): List<Course>

    suspend fun getCoursesFlow(): Flow<List<Course>>

    suspend fun saveCourses(courses: List<Course>)

}