package com.example.profnotes.presentation.ui.items

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.profnotes.R
import com.example.profnotes.data.model.course.Course
import com.example.profnotes.databinding.ItemCourseBinding
import com.example.profnotes.presentation.extensions.inflate
import com.example.profnotes.presentation.ui.home.ViewPagerDetailAdapter
import com.example.profnotes.presentation.ui.views.FlexboxSpaceItemDecoration
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager

class CourseViewHolder(
    parent: ViewGroup,
    private val onItemClick: (Course) -> Unit
) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_course)) {

    private val binding by viewBinding(ItemCourseBinding::bind)
    private val detailAdapter = ViewPagerDetailAdapter()

    init {
        binding.detailRecyclerView.apply {
            adapter = detailAdapter
            layoutManager = FlexboxLayoutManager(context).apply {
                flexDirection = FlexDirection.ROW
                flexWrap = FlexWrap.WRAP
            }
            addItemDecoration(FlexboxSpaceItemDecoration())
        }
        binding.cardView.layoutParams.apply {
            this.height = ViewGroup.LayoutParams.WRAP_CONTENT
        }
    }

    fun bind(course: Course) {
        with(binding) {
            textViewTitle.text = course.title
            textViewPage.text = (bindingAdapterPosition + 1).toString()
            textViewPage.typefaceId = R.font.roboto_bold
        }
        itemView.setOnClickListener { onItemClick(course) }
        detailAdapter.submitList(course.tags)
    }
}