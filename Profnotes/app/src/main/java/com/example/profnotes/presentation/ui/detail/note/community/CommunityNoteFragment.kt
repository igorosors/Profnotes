package com.example.profnotes.presentation.ui.detail.note.community

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.profnotes.R
import com.example.profnotes.databinding.FragmentCommunityNoteBinding
import com.example.profnotes.presentation.extensions.applyBottomInsets
import com.example.profnotes.presentation.extensions.applyTopInsets
import com.example.profnotes.presentation.ui.base.BaseFragment
import com.example.profnotes.presentation.ui.detail.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CommunityNoteFragment : BaseFragment(R.layout.fragment_community_note) {

    private val args: CommunityNoteFragmentArgs by navArgs()
    private val binding by viewBinding(FragmentCommunityNoteBinding::bind)
    private val viewModel: DetailViewModel by viewModels()

    @Inject
    lateinit var noteAdapter: CommunityNoteAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.appBarLayout.applyTopInsets()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            binding.recyclerView.applyBottomInsets()
        }
        binding.recyclerView.adapter = noteAdapter
        var note = args.note

        viewModel.contentLiveData.observe(viewLifecycleOwner) { state ->
            binding.stateViewFlipper.setState(state)
            state.doOnSuccess {
                noteAdapter.submitList(
                    listOf(
                        args.note,
                        *it.toTypedArray()
                    )
                )
            }
            if (note.isFavorite) {
                binding.buttonFavourite.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_favourite))
            } else {
                binding.buttonFavourite.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_not_favourite))
            }
            binding.buttonFavourite.setOnClickListener {
                if (note.isFavorite) {
                    binding.buttonFavourite.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_not_favourite))
                    note = note.copy(isFavorite = false)
                    viewModel.saveNote(note)
                } else {
                    binding.buttonFavourite.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_favourite))
                    note = note.copy(isFavorite = true)
                    viewModel.saveNote(note)
                }
            }
        }
        viewModel.getContentData(requireContext(), note.content)
    }
}