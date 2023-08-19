package com.example.profnotes.presentation.ui.note

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.doOnPreDraw
import androidx.core.view.updatePadding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.profnotes.R
import com.example.profnotes.data.model.LoadingState
import com.example.profnotes.databinding.FragmentNoteBinding
import com.example.profnotes.presentation.extensions.applyTopInsets
import com.example.profnotes.presentation.ui.views.NoteItemDecoration
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NoteFragment : Fragment(R.layout.fragment_note) {

    companion object {
        const val REQUEST_KEY = "request_key"
        const val EXTRA_URL = "extra_url"
        const val ITEM_KEY = "item_key"
        const val ITEM_TEXT = 0
        const val ITEM_IMAGE = 1
        const val TYPE_LOCAL = 0
        const val TYPE_COMMUNITY = 1
    }

    private val binding by viewBinding(FragmentNoteBinding::bind)
    private val viewModel: NoteViewModel by viewModels()

    @Inject
    lateinit var noteAdapter: NoteAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.appBarLayout.applyTopInsets()
        applyBottomLayoutPadding(binding.recyclerView)
        setupRecycler()
        bindViewModel()
        with(binding) {
            imageButton.setOnClickListener {
                showBottomSheet()
            }
            textViewCreateNote.setOnClickListener {
                viewModel.saveNote(tabLayout.selectedTabPosition, editTextTitle.text.toString())
            }
        }

    }

    private fun applyBottomLayoutPadding(view: View) {
        binding.bottomLayout.doOnPreDraw {
            view.updatePadding(bottom = it.height)
        }
    }

    private fun bindViewModel() = with(viewModel) {
        noteLiveData.observe(viewLifecycleOwner) {
            Log.d("submit", it.toString())
            noteAdapter.submitList(it)
        }
        imageLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoadingState.Loading -> {
                    binding.imageButton.setColorFilter(ContextCompat.getColor(requireContext(), R.color.dark_gray))
                    binding.progressBar.visibility = View.VISIBLE
                }
                is LoadingState.Success -> {
                    binding.imageButton.setColorFilter(ContextCompat.getColor(requireContext(), R.color.white))
                    binding.progressBar.visibility = View.GONE
                    viewModel.addImage(state.data)
                }
                is LoadingState.Error -> {
                    binding.imageButton.setColorFilter(ContextCompat.getColor(requireContext(), R.color.white))
                    binding.progressBar.visibility = View.GONE
                    showSnackbar()
                }
            }
        }
    }

    private fun setupRecycler() = with(binding) {
        recyclerView.adapter = noteAdapter
        recyclerView.addItemDecoration(NoteItemDecoration())
        noteAdapter.onTextChange = { text, position ->
            viewModel.updateItem(text, position)
        }
    }

    private fun showBottomSheet() {
        val bottomSheet = BottomSheetFragment()
        bottomSheet.setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialog)
        childFragmentManager.setFragmentResultListener(REQUEST_KEY, viewLifecycleOwner) { _, bundle ->
            when (bundle.getInt(ITEM_KEY)) {
                ITEM_TEXT -> {
                    viewModel.addText()
                }
                ITEM_IMAGE -> {
                    viewModel.getImage(
                        requireContext(),
                        bundle.getString(EXTRA_URL, "")
                    )
                }
                else -> {
                    throw Exception("Unsupported item type")
                }
            }
            bottomSheet.dismiss()
        }
        bottomSheet.show(childFragmentManager, "tag")
    }

    private fun showSnackbar() {
        Snackbar.make(
            requireView(),
            getString(R.string.failed_to_load_image),
            Snackbar.LENGTH_SHORT
        ).apply {
            setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.red))
        }.show()
    }
}


