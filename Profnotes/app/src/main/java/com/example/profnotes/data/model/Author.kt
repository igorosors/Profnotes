package com.example.profnotes.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Author(
    val id: String,
    val name: String,
    val surname: String,
    val role: String,
) : Parcelable