package com.example.profnotes.presentation.ui.views

import android.content.Context
import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.TextAppearanceSpan
import android.view.View
import androidx.core.content.res.ResourcesCompat

/**
 * Позволяет получать fontFamily на API < 26
 * Используется в SpannableString вместо TextAppearanceSpan
 */
class FontAwareTextAppearanceSpan(
    private val context: Context,
    appearance: Int
) : TextAppearanceSpan(context, appearance) {

    override fun updateMeasureState(ds: TextPaint) {
        super.updateMeasureState(ds)

        val font = getFont() ?: Typeface.DEFAULT
        val oldStyle = ds.typeface?.style ?: 0
        ds.typeface = Typeface.create(font, oldStyle)
    }

    private fun getFont(): Typeface? {
        val cleanFamilyName = family.removePrefix("res/font/").removeSuffix(".xml")
        val appPackageName = context.applicationContext.packageName
        val id = context.resources.getIdentifier(cleanFamilyName, "font", appPackageName)
        return ResourcesCompat.getFont(context, id)
    }
}