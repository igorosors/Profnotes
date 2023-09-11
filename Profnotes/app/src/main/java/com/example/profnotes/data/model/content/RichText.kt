package com.example.profnotes.data.model.content

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RichText(
    val text: String?,
    val url: String?,
) : Parcelable

