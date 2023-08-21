package com.example.profnotes.presentation.ui.items

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.profnotes.R
import com.example.profnotes.data.model.Note
import com.example.profnotes.databinding.ItemLocalNoteBinding
import com.example.profnotes.presentation.extensions.inflate
import java.text.SimpleDateFormat
import java.util.*

class LocalNoteViewHolder (
    parent: ViewGroup,
    private val onItemClick: (Note) -> Unit,
) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_local_note)) {

    private val binding by viewBinding(ItemLocalNoteBinding::bind)

    fun bind(note: Note) {
        with(binding) {
            val dateFormat = SimpleDateFormat("d MMMM", Locale.getDefault())
            val date = Date(note.date)
            titleTextView.text = note.title
            contentTextView.text = note.content.getOrNull(0)?.text
            dateTextView.text = dateFormat.format(date)
        }
        itemView.setOnClickListener { onItemClick(note) }
    }
}