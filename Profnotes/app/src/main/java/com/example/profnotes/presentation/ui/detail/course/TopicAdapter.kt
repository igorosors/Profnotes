package com.example.profnotes.presentation.ui.detail.course

import android.view.ViewGroup
import com.example.profnotes.presentation.ui.base.BaseAdapter
import javax.inject.Inject

class TopicAdapter : BaseAdapter<String, TopicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder =
        TopicViewHolder(parent)

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        holder.bind(items[position])
    }
}