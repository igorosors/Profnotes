package com.example.profnotes.presentation.ui.note

import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updatePadding
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.profnotes.R
import com.example.profnotes.data.model.content.ContentData
import com.example.profnotes.databinding.ItemNoteDataBinding
import com.example.profnotes.presentation.extensions.inflate
import com.example.profnotes.presentation.extensions.toPx

class NoteViewHolder(
    parent: ViewGroup,
    private val onTextChange: (String?, Int) -> Unit,
) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_note_data)) {

    private val binding by viewBinding(ItemNoteDataBinding::bind)
    private var afterTextChangedCallback: TextWatcher? = null

    fun bind(contentData: ContentData) {
        with(binding) {
            if (contentData.text == null) {
                editText.visibility = View.GONE
            }
            if (contentData.url == null) {
                imageView.visibility = View.GONE
            } else {
                imageView.visibility = View.VISIBLE
                if (contentData.text != null) {
                    editText.updatePadding(0, 0, 0, 8.toPx())
                }
            }
            afterTextChangedCallback.let { editText.removeTextChangedListener(it) }
            editText.setText(contentData.text)
            afterTextChangedCallback = editText.doAfterTextChanged { text ->
                onTextChange(text.toString(), bindingAdapterPosition)
            }
            imageView.setImageBitmap(contentData.bitmap)
        }
    }
}