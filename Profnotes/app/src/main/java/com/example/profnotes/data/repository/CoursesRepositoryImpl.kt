package com.example.profnotes.data.repository

import com.example.profnotes.data.mapper.CourseMapper
import com.example.profnotes.data.mapper.ErrorMapper
import com.example.profnotes.data.model.Course
import com.example.profnotes.data.remote.ApiService
import retrofit2.HttpException
import javax.inject.Inject

class CoursesRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val courseMapper: CourseMapper,
    private val errorMapper: ErrorMapper,
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
}
