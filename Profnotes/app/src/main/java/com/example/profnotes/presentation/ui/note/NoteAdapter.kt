package com.example.profnotes.presentation.ui.note

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.profnotes.presentation.ui.note.model.NoteData
import javax.inject.Inject

class NoteAdapter @Inject constructor() :
    RecyclerView.Adapter<NoteViewHolder>() {

    private val items = mutableListOf<NoteData>()
    lateinit var onTextChange: (String?, Int) -> Unit

    override fun getItemCount() = items.size

    private fun getItem(position: Int) = items[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(parent, onTextChange)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun submitList(newItems: List<NoteData>) {
        val newItem = newItems[newItems.size - 1]
        if (newItems.size > items.size) {
            items.add(newItem)
            notifyItemInserted(items.size - 1)

        } else {
            // Если изменился текст, не нужно вызывать notify, чтобы не сбросить фокус
            val size = items.size
            val oldUrl = items[size - 1].url
            val newUrl = newItems[size - 1].url
            items.clear()
            items.addAll(newItems)
            if (oldUrl != newUrl) {
                notifyItemChanged(size - 1)
            }
        }
    }

}

