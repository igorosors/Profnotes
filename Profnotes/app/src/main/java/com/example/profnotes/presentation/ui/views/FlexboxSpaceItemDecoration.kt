package com.example.profnotes.presentation.ui.views

import android.graphics.Rect
import android.view.View
import androidx.annotation.Px
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayoutManager

class FlexboxSpaceItemDecoration(
    @Px private val horizontalSpace: Int = 0,
    @Px private val verticalSpace: Int = 0,
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        makeOffsets(outRect, view, parent, state)
    }

    private fun makeOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State): Rect {
        return when (parent.layoutManager) {
            is FlexboxLayoutManager -> makeLinearOffsets(outRect, view, parent, state)
            else -> throw IllegalStateException("RecyclerView have unsupported layout manager")
        }
    }

    private fun makeLinearOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State): Rect =
        outRect.apply {
            val position = parent.getChildAdapterPosition(view)
            if (position != RecyclerView.NO_POSITION) {
                top = verticalSpace / 2
                bottom = verticalSpace / 2
                left = horizontalSpace / 2
                right = horizontalSpace / 2
            }
        }
}
