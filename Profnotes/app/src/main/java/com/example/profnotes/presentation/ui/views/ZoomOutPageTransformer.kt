package com.example.profnotes.presentation.ui.views

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs
import kotlin.math.max

class ZoomOutPageTransformer : ViewPager2.PageTransformer {

    companion object {
        const val MIN_SCALE = 0.95f
    }

    override fun transformPage(view: View, position: Float) {
        val pageWidth = view.width
        val pageHeight = view.height

        val scaleFactor = max(MIN_SCALE, 1 - abs(position))
        val verticalMargin = pageHeight * (1 - scaleFactor) / 2
        val horizontalMargin = pageWidth * (1 - scaleFactor) / 2
        if (position < 0) {
            view.translationX = horizontalMargin - verticalMargin / 2
        } else {
            view.translationX = -horizontalMargin + verticalMargin / 2
        }
        view.scaleX = scaleFactor
        view.scaleY = scaleFactor

    }
}