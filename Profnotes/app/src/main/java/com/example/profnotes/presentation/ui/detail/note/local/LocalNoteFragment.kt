package com.example.profnotes.presentation.ui.detail.note.local

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.profnotes.R
import com.example.profnotes.databinding.FragmentLocalNoteBinding
import com.example.profnotes.presentation.extensions.applyBottomInsets
import com.example.profnotes.presentation.extensions.applyTopInsets
import com.example.profnotes.presentation.ui.base.BaseFragment
import com.example.profnotes.presentation.ui.detail.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LocalNoteFragment : BaseFragment(R.layout.fragment_local_note) {

    private val args: LocalNoteFragmentArgs by navArgs()
    private val binding by viewBinding(FragmentLocalNoteBinding::bind)
    private val viewModel: DetailViewModel by viewModels()

    @Inject
    lateinit var localNoteAdapter: LocalNoteAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.appBarLayout.applyTopInsets()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            binding.recyclerView.applyBottomInsets()
        }
        binding.recyclerView.adapter = localNoteAdapter
        var note = args.note

        viewModel.contentLiveData.observe(viewLifecycleOwner) { state ->
            binding.stateViewFlipper.setState(state)
            state.doOnSuccess {
                localNoteAdapter.submitList(
                    listOf(
                        null,
                        *it.toTypedArray()
                    )
                )
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
        }
        viewModel.getContentData(requireContext(), note.content)
    }
}