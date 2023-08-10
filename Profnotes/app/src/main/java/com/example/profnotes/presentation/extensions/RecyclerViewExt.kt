package com.example.profnotes.presentation.extensions

import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.profnotes.presentation.ui.views.GridSpaceItemDecoration
import com.example.profnotes.presentation.ui.views.LinearSpaceItemDecoration

fun ViewPager2.addLinearSpaceItemDecoration(
    @DimenRes spacing: Int,
    showFirstVerticalDivider: Boolean = false,
    showLastVerticalDivider: Boolean = false,
    showFirstHorizontalDivider: Boolean = false,
    showLastHorizontalDivider: Boolean = false,
    conditionProvider: LinearSpaceItemDecoration.ConditionProvider? = null,
) {
    this.addItemDecoration(
        LinearSpaceItemDecoration(
            context.resources.getDimensionPixelSize(spacing),
            showFirstVerticalDivider,
            showLastVerticalDivider,
            showFirstHorizontalDivider,
            showLastHorizontalDivider,
            conditionProvider,
        )
    )
}

fun RecyclerView.addGridSpaceItemDecoration(
    spanCount: Int,
    @DimenRes spacing: Int,
    includeEdge: Boolean = false,
    excludeTopEdge: Boolean = true
) {
    val itemDecoration = GridSpaceItemDecoration(
        spanCount,
        context.resources.getDimensionPixelSize(spacing),
        includeEdge,
        excludeTopEdge
    )
    this.addItemDecoration(itemDecoration)
}