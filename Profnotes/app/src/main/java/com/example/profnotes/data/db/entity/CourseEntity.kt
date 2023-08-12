package com.example.profnotes.data.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.profnotes.data.db.dao.CourseDao
import kotlinx.parcelize.Parcelize

@Entity(tableName = CourseDao.COURSE_TABLE)
data class CourseEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "tags") val tags: String,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "plannedDate") val plannedDate: String,
    @ColumnInfo(name = "textList") val textList: String,
)