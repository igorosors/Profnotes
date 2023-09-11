package com.example.profnotes.presentation.ui.items

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.profnotes.data.model.course.Course
import com.example.profnotes.data.model.Data
import com.example.profnotes.data.model.note.Note
import com.example.profnotes.presentation.ui.base.BaseAdapter
import com.example.profnotes.presentation.ui.items.ItemsFragment.Companion.COMMUNITY_NOTE_ITEM
import com.example.profnotes.presentation.ui.items.ItemsFragment.Companion.COURSE_ITEM
import com.example.profnotes.presentation.ui.items.ItemsFragment.Companion.LOCAL_NOTE_ITEM
import javax.inject.Inject

class ItemsAdapter @Inject constructor() : BaseAdapter<Data, RecyclerView.ViewHolder>() {

    lateinit var onItemClick: (Data) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            COURSE_ITEM -> CourseViewHolder(parent, onItemClick)
            LOCAL_NOTE_ITEM -> LocalNoteViewHolder(parent, onItemClick)
            COMMUNITY_NOTE_ITEM -> CommunityNotesViewHolder(parent, onItemClick)
            else -> throw Exception("Unsupported view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            COURSE_ITEM -> (holder as CourseViewHolder).bind(items[position] as Course)
            LOCAL_NOTE_ITEM -> (holder as LocalNoteViewHolder).bind(items[position] as Note)
            COMMUNITY_NOTE_ITEM -> (holder as CommunityNotesViewHolder).bind(items[position] as Note)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            items[position] is Course -> COURSE_ITEM
            items[position] is Note && (items[position] as Note).author == null -> LOCAL_NOTE_ITEM
            else -> COMMUNITY_NOTE_ITEM
        }
    }
}