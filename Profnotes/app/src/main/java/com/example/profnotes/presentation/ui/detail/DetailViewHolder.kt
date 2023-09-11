package com.example.profnotes.presentation.ui.detail

import android.view.View
import android.view.ViewGroup
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.profnotes.R
import com.example.profnotes.data.model.content.ContentData
import com.example.profnotes.databinding.ItemDetailContentBinding
import com.example.profnotes.presentation.extensions.inflate
import com.example.profnotes.presentation.extensions.toPx

class DetailViewHolder(
    parent: ViewGroup,
) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_detail_content)) {

    private val binding by viewBinding(ItemDetailContentBinding::bind)

    fun bind(contentData: ContentData) {
        with(binding) {
            if (bindingAdapterPosition != 1) {
                binding.layout.updatePadding(0, 10.toPx(), 0, 0)
            }
            if (contentData.text == null) {
                textView.visibility = View.GONE
            }
            if (contentData.url == null) {
                imageView.visibility = View.GONE
            } else if (contentData.text != null) {
                textView.updatePadding(0, 0, 0, 8.toPx())
            }

            textView.text = contentData.text
            imageView.setImageBitmap(contentData.bitmap)
        }
    }
}