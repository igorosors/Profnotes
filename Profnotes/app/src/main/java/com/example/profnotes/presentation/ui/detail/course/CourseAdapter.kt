package com.example.profnotes.presentation.ui.detail.course

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.profnotes.data.model.content.ContentData
import com.example.profnotes.data.model.course.Course
import com.example.profnotes.presentation.ui.base.BaseAdapter
import com.example.profnotes.presentation.ui.detail.DetailViewHolder
import javax.inject.Inject

class CourseAdapter @Inject constructor() : BaseAdapter<Any, RecyclerView.ViewHolder>() {
    companion object {
        private const val HEADER_ITEM = 0
        private const val DATA_ITEM = 1
    }

    lateinit var onStatusBarClick: () -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == HEADER_ITEM) CourseHeaderViewHolder(parent, onStatusBarClick)
        else DetailViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            HEADER_ITEM -> (holder as CourseHeaderViewHolder).bind(items[position] as Course)
            DATA_ITEM -> (holder as DetailViewHolder).bind(items[position] as ContentData)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) HEADER_ITEM
        else DATA_ITEM
    }
}