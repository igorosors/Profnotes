package com.example.profnotes.presentation.ui.home

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.profnotes.R
import com.example.profnotes.databinding.FragmentHomeBinding
import com.example.profnotes.presentation.extensions.applyTopInsets
import com.example.profnotes.presentation.extensions.toPx
import com.example.profnotes.presentation.ui.base.BaseFragment
import com.example.profnotes.presentation.ui.views.FontAwareTextAppearanceSpan
import com.example.profnotes.presentation.ui.views.ViewPagerItemDecoration
import com.example.profnotes.presentation.ui.views.ZoomOutPageTransformer
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun callOperations() {
        viewModel.subscribeToCourses()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.refreshLayout.setOnRefreshListener {
            viewModel.getData(true)
            binding.viewPager.currentItem = 0
        }
        binding.appBarLayout.applyTopInsets()
        setupViewPager()

        binding.localNote.nameLayout.visibility = View.INVISIBLE

        viewModel.homeLiveData.observe(viewLifecycleOwner) { state ->
            binding.stateViewFlipper.setState(state)
            state.doOnLoading {
                binding.appBarLayout.visibility = View.GONE
            }
            state.doOnSuccess { data ->
                with(binding) {
                    refreshLayout.isRefreshing = false
                    appBarLayout.visibility = View.VISIBLE
                    textViewCoursesRemaining.visibility = View.VISIBLE
                    textViewCoursesCompleted.visibility = View.VISIBLE

                    Log.d("loc notes", data.localNotes.size.toString())
                    Log.d("cum notes", data.communityNotes.size.toString())
                    if (data.localNotes.isNotEmpty()) {
                        val lastNote = data.localNotes[data.localNotes.size - 1]
                        val date = lastNote.date
                        val calendar = Calendar.getInstance().apply { timeInMillis = date.toLong() }
                        Log.d("loc cum", lastNote.content[0].image.toString())
                        localNote.titleTextView.text = lastNote.title
                        localNote.contentTextView.text = lastNote.content.getOrNull(0)?.text
                        localNote.dateTextView.text = calendar.get(Calendar.DAY_OF_MONTH).toString() + " " + (calendar.get(Calendar.MONTH) + 1).toString()
                    }
                }

                viewPagerAdapter.submitList(data.courses)
                // Отступы между табами, должны применяться после установки элементов в view pager
                setupTabLayout()

                val title = "Ближайшее занятие\nСегодня в 12:00"
                binding.textViewToolbarTitle.text = SpannableStringBuilder(title).apply {
                    setSpan(FontAwareTextAppearanceSpan(requireContext(), R.style.FirstLineTextStyle), 0, 17, 0)
                    setSpan(FontAwareTextAppearanceSpan(requireContext(), R.style.SecondLineTextStyle), 18, title.length, 0)
                }

                val coursesCompleted = "Пройдено\n2 курса"
                binding.textViewCoursesCompleted.text = SpannableStringBuilder(coursesCompleted).apply {
                    setSpan(FontAwareTextAppearanceSpan(requireContext(), R.style.FirstLineTextStyle), 0, 8, 0)
                    setSpan(
                        FontAwareTextAppearanceSpan(requireContext(), R.style.SecondLineTextStyle),
                        9,
                        coursesCompleted.length,
                        0
                    )
                }

                val coursesRemaining = "Осталось\n14 занятий"
                binding.textViewCoursesRemaining.text = SpannableStringBuilder(coursesRemaining).apply {
                    setSpan(FontAwareTextAppearanceSpan(requireContext(), R.style.FirstLineTextStyle), 0, 8, 0)
                    setSpan(
                        FontAwareTextAppearanceSpan(requireContext(), R.style.SecondLineTextStyle),
                        9,
                        coursesRemaining.length,
                        0
                    )
                }
            }
            state.doOnError {
                with(binding) {
                    appBarLayout.visibility = View.VISIBLE
                    textViewCoursesRemaining.visibility = View.GONE
                    textViewCoursesCompleted.visibility = View.GONE

                    val title = "Ближайшее занятие\nНеизвестно"
                    binding.textViewToolbarTitle.text = SpannableStringBuilder(title).apply {
                        setSpan(FontAwareTextAppearanceSpan(requireContext(), R.style.FirstLineTextStyle), 0, 17, 0)
                        setSpan(FontAwareTextAppearanceSpan(requireContext(), R.style.SecondLineTextStyle), 18, title.length, 0)
                    }
                }
            }
        }

    }

    private fun setupViewPager() = with(binding.viewPager) {
        adapter = viewPagerAdapter
        setPageTransformer(ZoomOutPageTransformer())
        addItemDecoration(ViewPagerItemDecoration(4.toPx()))
        (getChildAt(0) as RecyclerView).apply {
            clipToPadding = false
            setPadding(12.toPx(), 0, 12.toPx(), 0)
        }
        TabLayoutMediator(binding.tabLayout, this) { _, _ -> }.attach()
        viewPagerAdapter.onItemClick = {
            // TODO
        }
    }

    private fun setupTabLayout() = with(binding) {
        // Отступы между табами
        for (i in 0 until tabLayout.tabCount - 1) {
            val tab = (tabLayout.getChildAt(0) as ViewGroup).getChildAt(i)
            val layoutParams = tab.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.setMargins(0, 0, 2.toPx(), 0)
            tab.requestLayout()
        }
    }

}