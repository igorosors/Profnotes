package com.example.profnotes.presentation.ui.home

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.profnotes.R
import com.example.profnotes.databinding.ItemViewpagerDetailBinding
import com.example.profnotes.presentation.extensions.inflate

class ViewPagerDetailViewHolder(
    parent: ViewGroup,
) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_viewpager_detail)) {
    private val binding by viewBinding (ItemViewpagerDetailBinding::bind)

    fun bind(text: String) {
        binding.textView.text = text


    }
}