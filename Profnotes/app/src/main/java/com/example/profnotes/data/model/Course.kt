package com.example.profnotes.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Course(
    val id: String,
    val title: String,
    val description: String,
    val tags: List<String>,
    val status: String,
    val plannedDate: String,
    val content: List<RichText>,
) : Data, Parcelable