package com.example.profnotes.presentation.ui.detail.course

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.profnotes.R
import com.example.profnotes.data.model.course.Course
import com.example.profnotes.databinding.CourseHeaderBinding
import com.example.profnotes.presentation.extensions.inflate
import com.example.profnotes.presentation.extensions.toPx
import com.example.profnotes.presentation.ui.views.DataItemDecoration

class CourseHeaderViewHolder(
    parent: ViewGroup,
    private val onStatusBarClick: () -> Unit,
) : RecyclerView.ViewHolder(parent.inflate(R.layout.course_header)) {

    private val binding by viewBinding(CourseHeaderBinding::bind)

    private val topicAdapter = TopicAdapter()

    fun bind(course: Course) {
        with(binding) {
            textViewStatus.text = course.status
            layoutStatus.setOnClickListener {
                onStatusBarClick()
            }
            recyclerView.adapter = topicAdapter
            recyclerView.addItemDecoration(DataItemDecoration(4.toPx()))
            topicAdapter.submitList(course.tags)
        }
    }
}
