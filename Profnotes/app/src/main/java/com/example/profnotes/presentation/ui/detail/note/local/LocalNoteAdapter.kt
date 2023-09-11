package com.example.profnotes.presentation.ui.detail.note.local

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.profnotes.data.model.content.ContentData
import com.example.profnotes.presentation.ui.base.BaseAdapter
import com.example.profnotes.presentation.ui.detail.DetailViewHolder
import javax.inject.Inject

class LocalNoteAdapter @Inject constructor() : BaseAdapter<ContentData?, RecyclerView.ViewHolder>() {
    companion object {
        private const val HEADER_ITEM = 0
        private const val DATA_ITEM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HEADER_ITEM -> LocalNoteHeaderViewHolder(parent)
            DATA_ITEM -> DetailViewHolder(parent)
            else -> throw Exception("Unsupported view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA_ITEM)
            (holder as DetailViewHolder).bind(items[position] as ContentData)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) HEADER_ITEM
        else DATA_ITEM
    }
}