package com.example.profnotes.data.mapper

import com.example.profnotes.data.model.Course
import com.example.profnotes.data.model.RichText
import com.example.profnotes.data.remote.model.ApiCourse
import com.example.profnotes.data.remote.model.ApiRichText
import javax.inject.Inject

class CourseMapper @Inject constructor() {
    fun fromApiToModel(course: ApiCourse): Course {
        return Course(
            id = course.id ?: "0",
            title = course.title.orEmpty(),
            description = course.description.orEmpty(),
            tags = course.tags.orEmpty(),
            status = course.status.orEmpty(),
            plannedDate = course.plannedDate.orEmpty(),
            textList = course.textList.orEmpty().map { fromApiToModel(it) },
        )
    }

    private fun fromApiToModel(richText: ApiRichText): RichText {
        return RichText(
            text = richText.text.orEmpty(),
            image = richText.image.orEmpty()
        )
    }
}