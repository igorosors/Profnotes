package com.example.profnotes.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RichText(
    val text: String?,
    val image: String?,
) : Parcelable

