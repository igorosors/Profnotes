package com.example.profnotes.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.profnotes.data.db.entity.CourseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {
    companion object {
        const val COURSE_TABLE = "courses"
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCourse(vararg courseEntity: CourseEntity)

    @Query("select * from $COURSE_TABLE")
    fun getCoursesFlow(): Flow<List<CourseEntity>>

    @Query("select * from $COURSE_TABLE where title like '%' || :text || '%'")
    fun searchCourses(text: String): Flow<List<CourseEntity>>

    @Query("select * from $COURSE_TABLE where isFavorite = 1")
    fun getFavouriteCoursesFlow(): Flow<List<CourseEntity>>

    @Query("select * from $COURSE_TABLE where title like '%' || :text || '%' and isFavorite = 1")
    fun searchFavouriteCourses(text: String): Flow<List<CourseEntity>>
}