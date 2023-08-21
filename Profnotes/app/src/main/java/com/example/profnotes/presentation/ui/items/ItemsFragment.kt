package com.example.profnotes.presentation.ui.items

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.profnotes.R
import com.example.profnotes.databinding.FragmentItemsBinding
import com.example.profnotes.presentation.extensions.applyTopInsets
import com.example.profnotes.presentation.extensions.toPx
import com.example.profnotes.presentation.ui.base.BaseFragment
import com.example.profnotes.presentation.ui.views.NoteItemDecoration
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

    @Inject
    lateinit var itemsAdapter: ItemsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.appBarLayout.applyTopInsets()

        viewModel.dataLiveData.observe(viewLifecycleOwner) { state ->
            binding.stateViewFlipper.setState(state)
            state.doOnSuccess {
                itemsAdapter.submitList(it)
            }
        }
        binding.recyclerView.adapter = itemsAdapter
        binding.recyclerView.addItemDecoration(NoteItemDecoration(16.toPx()))
        itemsAdapter.onItemClick = {
            // TODO
        }
    }

    override fun callOperations() {
        when (args.itemType) {
            COURSE_ITEM -> {
                binding.toolbar.title = requireContext().getString(R.string.courses_label)
                viewModel.subscribeToCourses()
            }
            LOCAL_NOTE_ITEM -> {
                binding.toolbar.title = requireContext().getString(R.string.local_notes_label)
                viewModel.subscribeToLocalNotes()
            }
            COMMUNITY_NOTE_ITEM -> {
                binding.toolbar.title = requireContext().getString(R.string.community_notes_label)
                viewModel.subscribeToCommunityNotes()
            }
        }
    }
}