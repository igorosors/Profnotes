package com.example.profnotes.data.mapper

import com.example.profnotes.data.db.entity.CourseEntity
import com.example.profnotes.data.model.course.Course
import com.example.profnotes.data.model.content.RichText
import com.example.profnotes.data.remote.model.ApiCourse
import com.example.profnotes.data.remote.model.ApiRichText
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class CourseMapper @Inject constructor(
    private val gson: Gson
) {
    fun fromApiToModel(course: ApiCourse): Course {
        return Course(
            id = course.id ?: "0",
            title = course.title.orEmpty(),
            description = course.description.orEmpty(),
            tags = course.tags.orEmpty(),
            status = course.status.orEmpty(),
            plannedDate = course.plannedDate.orEmpty(),
            content = course.textList.orEmpty().map { fromApiToModel(it) },
        )
    }

    private fun fromApiToModel(richText: ApiRichText): RichText {
        return RichText(
            text = richText.text.orEmpty(),
            url = richText.image.orEmpty()
        )
    }

    fun fromEntityToModel(course: CourseEntity): Course {
        val tagsTypeToken = object : TypeToken<List<String>>() {}.type
        val contentTypeToken = object : TypeToken<List<RichText>>() {}.type
        return Course(
            id = course.id,
            isFavorite = course.isFavorite == 1,
            title = course.title,
            description = course.description,
            tags = gson.fromJson(course.tags, tagsTypeToken),
            status = course.status,
            plannedDate = course.plannedDate,
            content = gson.fromJson(course.content, contentTypeToken),
        )
    }

    fun fromModelToEntity(course: Course): CourseEntity {
        return CourseEntity(
            id = course.id,
            isFavorite = if (course.isFavorite) 1 else 0,
            title = course.title,
            description = course.description,
            tags = gson.toJson(course.tags),
            status = course.status,
            plannedDate = course.plannedDate,
            content = gson.toJson(course.content)
        )
    }
}

