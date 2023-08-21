package com.example.profnotes.presentation.ui.views

import android.graphics.Rect
import android.view.View
import androidx.annotation.Px
import androidx.recyclerview.widget.RecyclerView
import com.example.profnotes.presentation.extensions.toPx

class FlexboxSpaceItemDecoration(private val space: Int = 4.toPx()) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        outRect.apply {
            top = space / 2
            bottom = space / 2
            left = space / 2
            right = space / 2
        }
    }
}
