package com.example.profnotes.presentation.ui.detail.course

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.profnotes.R
import com.example.profnotes.databinding.FragmentCourseBinding
import com.example.profnotes.presentation.extensions.applyBottomInsets
import com.example.profnotes.presentation.extensions.applyTopInsets
import com.example.profnotes.presentation.ui.base.BaseFragment
import com.example.profnotes.presentation.ui.detail.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CourseFragment : BaseFragment(R.layout.fragment_course) {

    private val args: CourseFragmentArgs by navArgs()
    private val binding by viewBinding(FragmentCourseBinding::bind)
    private val viewModel: DetailViewModel by viewModels()

    @Inject
    lateinit var courseAdapter: CourseAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.appBarLayout.applyTopInsets()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            binding.recyclerView.applyBottomInsets()
        }
        binding.recyclerView.adapter = courseAdapter
        courseAdapter.onStatusBarClick = {
            // TODO
        }
        var course = args.course
        viewModel.contentLiveData.observe(viewLifecycleOwner) { state ->
            binding.stateViewFlipper.setState(state)
            state.doOnSuccess {
                courseAdapter.submitList(
                    listOf(
                        course,
                        *it.toTypedArray()
                    )
                )
                if (course.isFavorite) {
                    binding.buttonFavourite.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_favourite))
                } else {
                    binding.buttonFavourite.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_not_favourite))
                }
                binding.buttonFavourite.setOnClickListener {
                    if (course.isFavorite) {
                        binding.buttonFavourite.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_not_favourite))
                        course = course.copy(isFavorite = false)
                        viewModel.saveCourse(course)
                    } else {
                        binding.buttonFavourite.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_favourite))
                        course = course.copy(isFavorite = true)
                        viewModel.saveCourse(course)
                    }
                }
            }
        }
        viewModel.getContentData(requireContext(), course.content)
    }
}