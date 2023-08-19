package com.example.profnotes.presentation.ui.note

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.profnotes.R
import com.example.profnotes.databinding.ItemNoteDataBinding
import com.example.profnotes.presentation.extensions.inflate
import com.example.profnotes.presentation.ui.note.model.NoteData

class NoteViewHolder(
    parent: ViewGroup,
    private val onTextChange: (String?, Int) -> Unit
) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_note_data)) {

    private val binding by viewBinding(ItemNoteDataBinding::bind)

    fun bind(noteData: NoteData, position: Int) {
        with(binding) {
            if (noteData.text == null) {
                editText.visibility = View.GONE
            }
            if (noteData.url == null) {
                imageView.visibility = View.GONE
            } else {
                imageView.visibility = View.VISIBLE
            }
            editText.setText(noteData.text)
            Log.d("after setText", noteData.text.toString() + " at pos " + position.toString())
            editText.doAfterTextChanged { text ->
                Log.d("DOAFTERCHANGED", "CHANGED")
                onTextChange(text.toString(), position)
            }
            imageView.setImageBitmap(noteData.bitmap)
        }
    }
}