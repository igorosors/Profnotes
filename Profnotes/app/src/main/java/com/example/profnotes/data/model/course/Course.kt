package com.example.profnotes.data.model.course

import android.os.Parcelable
import com.example.profnotes.data.model.Data
import com.example.profnotes.data.model.content.RichText
import kotlinx.parcelize.Parcelize

@Parcelize
data class Course(
    val id: String,
    val isFavorite: Boolean = false,
    val title: String,
    val description: String,
    val tags: List<String>,
    val status: String,
    val plannedDate: String,
    val content: List<RichText>,
) : Data, Parcelable