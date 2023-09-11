package com.example.profnotes.presentation.ui.home

import android.view.ViewGroup
import com.example.profnotes.data.model.course.Course
import com.example.profnotes.presentation.ui.base.BaseAdapter
import javax.inject.Inject

class ViewPagerAdapter @Inject constructor() : BaseAdapter<Course, ViewPagerViewHolder>() {

    lateinit var onItemClick: (Course) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        return ViewPagerViewHolder(parent, onItemClick)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}