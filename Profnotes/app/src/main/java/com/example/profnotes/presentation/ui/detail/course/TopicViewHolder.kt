package com.example.profnotes.presentation.ui.detail.course

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.profnotes.R
import com.example.profnotes.databinding.ItemCourseTopicBinding
import com.example.profnotes.presentation.extensions.inflate

class TopicViewHolder(
    parent: ViewGroup,
) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_course_topic)) {

    private val binding by viewBinding(ItemCourseTopicBinding::bind)

    fun bind(topic: String) {
        binding.textView.text = topic
    }
}