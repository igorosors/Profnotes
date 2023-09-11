package com.example.profnotes.data.repository

import com.example.profnotes.data.model.course.Course
import kotlinx.coroutines.flow.Flow

interface CoursesRepository {

    suspend fun getCourses(): List<Course>

    suspend fun getCoursesFlow(): Flow<List<Course>>

    suspend fun getFavouriteCoursesFlow(): Flow<List<Course>>

    suspend fun saveCourses(courses: List<Course>)

    suspend fun searchCourses(text: String): Flow<List<Course>>

    suspend fun searchFavouritesCourses(text: String): Flow<List<Course>>
}