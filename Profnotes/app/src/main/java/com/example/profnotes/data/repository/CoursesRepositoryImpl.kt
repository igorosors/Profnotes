package com.example.profnotes.data.repository

import com.example.profnotes.data.db.AppDatabase
import com.example.profnotes.data.mapper.CourseMapper
import com.example.profnotes.data.mapper.ErrorMapper
import com.example.profnotes.data.model.Course
import com.example.profnotes.data.remote.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import javax.inject.Inject

class CoursesRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val courseMapper: CourseMapper,
    private val errorMapper: ErrorMapper,
    private val database: AppDatabase
) : CoursesRepository {

    override suspend fun getCourses(): List<Course> {
        return try {
            apiService.getCourses()
                .data
                .map { courseMapper.fromApiToModel(it) }
        } catch (e: HttpException) {
            val apiError = errorMapper.fromApiToModel(e)
            throw apiError
        }
    }

    override suspend fun getCoursesFlow(): Flow<List<Course>> {
        return database.courseDao().getCoursesFlow().map { entities ->
            entities.map { courseMapper.fromEntityToModel(it) }
        }
    }

    override suspend fun saveCourses(courses: List<Course>) {
        database.courseDao().saveCourse(*courses.map { courseMapper.fromModelToEntity(it) }.toTypedArray())
    }
}
