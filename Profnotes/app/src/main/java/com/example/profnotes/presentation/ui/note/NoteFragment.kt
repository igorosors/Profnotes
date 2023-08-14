package com.example.profnotes.presentation.ui.note

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.profnotes.R
import com.example.profnotes.databinding.FragmentNoteBinding
import com.example.profnotes.presentation.extensions.applyTopInsets

class NoteFragment : Fragment(R.layout.fragment_note) {

    companion object {
        const val REQUEST_KEY = "request_key"
    }

    private val binding by viewBinding(FragmentNoteBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.appBarLayout.applyTopInsets()
        binding.imageButton.setOnClickListener {
            val bottomSheet = BottomSheetFragment()
            bottomSheet.setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialog)
            childFragmentManager.setFragmentResultListener(REQUEST_KEY, viewLifecycleOwner) { _, _ ->
                bottomSheet.dismiss()
            }
            bottomSheet.show(childFragmentManager, "tag")
        }


    }
}