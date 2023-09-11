package com.example.profnotes.presentation.ui.detail.note.community

import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.profnotes.R
import com.example.profnotes.data.model.note.Note
import com.example.profnotes.databinding.CommunityNoteHeaderBinding
import com.example.profnotes.presentation.extensions.inflate

class CommunityNoteHeaderViewHolder(
    private val parent: ViewGroup,
) : RecyclerView.ViewHolder(parent.inflate(R.layout.community_note_header)) {

    private val binding by viewBinding(CommunityNoteHeaderBinding::bind)

    fun bind(note: Note) {
        with(binding) {
            val author = "${note.author?.name.orEmpty()} ${note.author?.surname.orEmpty()}"
            imageViewAvatar.setImageDrawable(ContextCompat.getDrawable(parent.context, R.drawable.logo))
            textViewName.text = author
        }
    }
}