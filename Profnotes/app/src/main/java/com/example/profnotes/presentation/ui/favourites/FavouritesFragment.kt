package com.example.profnotes.presentation.ui.favourites

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.profnotes.R
import com.example.profnotes.data.model.home.HomeData
import com.example.profnotes.databinding.FragmentFavouritesBinding
import com.example.profnotes.presentation.extensions.applyTopInsets
import com.example.profnotes.presentation.extensions.toPx
import com.example.profnotes.presentation.ui.base.BaseFragment
import com.example.profnotes.presentation.ui.home.HomeFragmentDirections
import com.example.profnotes.presentation.ui.home.ViewPagerAdapter
import com.example.profnotes.presentation.ui.items.ItemsFragment
import com.example.profnotes.presentation.ui.views.ViewPagerItemDecoration
import com.example.profnotes.presentation.ui.views.ZoomOutPageTransformer
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class FavouritesFragment : BaseFragment(R.layout.fragment_favourites) {

    private val binding by viewBinding(FragmentFavouritesBinding::bind)
    private val viewModel: FavouritesViewModel by viewModels()

    @Inject
    lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.appBarLayout.applyTopInsets()
        bindViewModel()
        setupViewPager()
        setupTabLayout()
        setupSearch()
    }

    override fun callOperations() {
        viewModel.subscribeToData()
    }

    private fun bindViewModel() {
        viewModel.favouriteLiveData.observe(viewLifecycleOwner) { state ->
            binding.stateViewFlipper.setState(state)
            state.doOnSuccess { data ->
                with(binding) {
                    if (
                        data.courses.isEmpty() &&
                        data.localNotes.isEmpty() &&
                        data.communityNotes.isEmpty()
                    ) {
                        stateViewFlipper.setEmptyState()
                    } else {
                        setupCourses(data)
                        setupLocalNote(data)
                        setupCommunityNote(data)
                    }
                }
            }
        }
    }

    private fun setupSearch() {
        with(binding) {
            buttonSearch.setOnClickListener {
                toolbar.visibility = View.GONE
                editTextSearch.visibility = View.VISIBLE
                editTextSearch.requestFocus()
                val inputMethodManager  = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager .showSoftInput(editTextSearch, InputMethodManager.SHOW_IMPLICIT)
            }

            editTextSearch.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    editTextSearch.visibility = View.GONE
                    toolbar.visibility = View.VISIBLE
                    true
                } else {
                    false
                }
            }

            editTextSearch.doAfterTextChanged {
                viewModel.search(it.toString())
            }
        }
    }

    private fun setupCourses(data: HomeData) = with(binding) {
        if (data.courses.isEmpty()) {
            coursesLayout.visibility = View.GONE
            viewPager.visibility = View.GONE
            tabLayout.visibility = View.GONE
        } else {
            coursesLayout.visibility = View.VISIBLE
            viewPager.visibility = View.VISIBLE
            tabLayout.visibility = View.VISIBLE
            textViewCourses.setOnClickListener {

            }
            viewPagerAdapter.submitList(data.courses)
            viewPager.setCurrentItem(0, false)
            // Отступы между табами, должны применяться после установки элементов в view pager
            setupTabLayout()
        }
    }

    private fun setupLocalNote(data: HomeData) = with(binding) {
        binding.textViewLocalNotes.setOnClickListener {

        }
        if (data.localNotes.isNotEmpty()) {
            val lastNote = data.localNotes[data.localNotes.size - 1]
            binding.localNote.root.setOnClickListener {

            }
            val dateFormat = SimpleDateFormat("d MMMM", Locale.getDefault())
            val date = Date(lastNote.date)
            localLayout.visibility = View.VISIBLE
            localNote.root.visibility = View.VISIBLE
            with(localNote) {
                titleTextView.text = lastNote.title
                contentTextView.text = lastNote.content.getOrNull(0)?.text
                dateTextView.text = dateFormat.format(date)
            }
        } else {
            localLayout.visibility = View.GONE
            localNote.root.visibility = View.GONE
        }
    }

    private fun setupCommunityNote(data: HomeData) = with(binding) {
        binding.textViewCommunityNotes.setOnClickListener {

        }
        if (data.communityNotes.isNotEmpty()) {
            val lastNote = data.communityNotes[data.communityNotes.size - 1]
            binding.communityNote.root.setOnClickListener {

            }
            val author = "${lastNote.author?.name.orEmpty()} ${lastNote.author?.surname.orEmpty()}"
            val dateFormat = SimpleDateFormat("d MMMM", Locale.getDefault())
            val date = Date(lastNote.date)
            communityLayout.visibility = View.VISIBLE
            communityNote.root.visibility = View.VISIBLE
            with(communityNote) {
                titleTextView.text = lastNote.title
                contentTextView.text = lastNote.content.getOrNull(0)?.text
                textViewAuthor.text = author
                imageViewAvatar.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.logo))
                dateTextView.text = dateFormat.format(date)
            }
        } else {
            communityLayout.visibility = View.GONE
            communityNote.root.visibility = View.GONE
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