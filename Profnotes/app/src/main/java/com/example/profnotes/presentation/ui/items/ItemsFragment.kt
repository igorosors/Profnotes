package com.example.profnotes.presentation.ui.items

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.profnotes.R
import com.example.profnotes.data.model.Data
import com.example.profnotes.data.model.course.Course
import com.example.profnotes.data.model.note.Note
import com.example.profnotes.databinding.FragmentItemsBinding
import com.example.profnotes.presentation.extensions.applyTopInsets
import com.example.profnotes.presentation.extensions.toPx
import com.example.profnotes.presentation.ui.base.BaseFragment
import com.example.profnotes.presentation.ui.views.DataItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ItemsFragment : BaseFragment(R.layout.fragment_items) {
    companion object {
        const val COURSE_ITEM = 0
        const val LOCAL_NOTE_ITEM = 1
        const val COMMUNITY_NOTE_ITEM = 2
    }

    private val args: ItemsFragmentArgs by navArgs()
    private val binding by viewBinding(FragmentItemsBinding::bind)
    private val viewModel: ItemsViewModel by viewModels()
    lateinit var data: Data

    @Inject
    lateinit var itemsAdapter: ItemsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.appBarLayout.applyTopInsets()
        setupToolbar()

        viewModel.dataLiveData.observe(viewLifecycleOwner) { state ->
            binding.stateViewFlipper.setState(state)
            state.doOnSuccess {
                itemsAdapter.submitList(it)
            }
        }
        itemsAdapter.onItemClick = {
            findNavController().navigate(getAction(it))
        }
        binding.recyclerView.adapter = itemsAdapter
        binding.recyclerView.addItemDecoration(DataItemDecoration(16.toPx()))
    }

    override fun callOperations() {
        when (args.itemType) {
            COURSE_ITEM -> {
                viewModel.subscribeToCourses()
            }
            LOCAL_NOTE_ITEM -> {
                viewModel.subscribeToLocalNotes()
            }
            COMMUNITY_NOTE_ITEM -> {
                viewModel.subscribeToCommunityNotes()
            }
        }
    }

    private fun getAction(data: Data): NavDirections {
        return when (args.itemType) {
            COURSE_ITEM -> {
                ItemsFragmentDirections.actionItemsFragmentToCourseFragment(data as Course)
            }
            LOCAL_NOTE_ITEM -> {
                ItemsFragmentDirections.actionItemsFragmentToLocalNoteFragment(data as Note)
            }
            COMMUNITY_NOTE_ITEM -> {
                ItemsFragmentDirections.actionItemsFragmentToCommunityNoteFragment(data as Note)
            }
            else -> throw Exception("Unsupported item type")
        }
    }

    private fun setupToolbar() {
        when (args.itemType) {
            COURSE_ITEM -> {
                binding.toolbar.title = requireContext().getString(R.string.courses_label)
            }
            LOCAL_NOTE_ITEM -> {
                binding.toolbar.title = requireContext().getString(R.string.local_notes_label)
            }
            COMMUNITY_NOTE_ITEM -> {
                binding.toolbar.title = requireContext().getString(R.string.community_notes_label)
            }
        }
    }
}