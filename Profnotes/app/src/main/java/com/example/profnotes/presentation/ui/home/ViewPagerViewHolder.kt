package com.example.profnotes.presentation.ui.home

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.profnotes.R
import com.example.profnotes.databinding.ItemViewpagerBinding
import com.example.profnotes.presentation.extensions.inflate
import com.example.profnotes.data.model.Course
import com.example.profnotes.presentation.extensions.toPx
import com.example.profnotes.presentation.ui.views.FlexboxSpaceItemDecoration
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager

class ViewPagerViewHolder(
    parent: ViewGroup,
    private val onItemClick: (Course) -> Unit
) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_viewpager)) {

    private val binding by viewBinding(ItemViewpagerBinding::bind)
    private val viewPagerDetailAdapter = ViewPagerDetailAdapter()

    init {
        binding.detailRecyclerView.apply {
            adapter = viewPagerDetailAdapter
            layoutManager = FlexboxLayoutManager(context).apply {
                flexDirection = FlexDirection.ROW
                flexWrap = FlexWrap.WRAP
            }
            addItemDecoration(
                FlexboxSpaceItemDecoration(
                    horizontalSpace = 4.toPx(),
                    verticalSpace = 4.toPx(),
                )
            )
        }
    }

    fun bind(course: Course, position: Int) {
        with(binding) {
            textViewTitle.text = course.title
            cardView.setOnClickListener {
                onItemClick(course)
            }
            textViewPage.text = (position + 1).toString()
            textViewPage.typefaceId = R.font.roboto_bold
        }
        viewPagerDetailAdapter.submitList(course.tags)
    }
}