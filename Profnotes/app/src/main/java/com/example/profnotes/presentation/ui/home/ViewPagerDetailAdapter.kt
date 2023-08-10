package com.example.profnotes.presentation.ui.home

import android.view.ViewGroup
import com.example.profnotes.presentation.ui.base.BaseAdapter
import javax.inject.Inject

class ViewPagerDetailAdapter : BaseAdapter<String, ViewPagerDetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerDetailViewHolder {
        return ViewPagerDetailViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ViewPagerDetailViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}