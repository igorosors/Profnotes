package com.example.profnotes.presentation.ui.views

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.profnotes.presentation.extensions.toPx

class ViewPagerItemDecoration(private var space: Int = 4.toPx()) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        outRect.left = space
        outRect.right = space
    }
}