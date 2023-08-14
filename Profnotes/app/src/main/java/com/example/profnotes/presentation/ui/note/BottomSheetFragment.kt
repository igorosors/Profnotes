package com.example.profnotes.presentation.ui.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.profnotes.R
import com.example.profnotes.databinding.FragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment : BottomSheetDialogFragment() {

    private val binding by viewBinding(FragmentBottomSheetBinding::bind)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_bottom_sheet, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            textViewText.setOnClickListener {
                parentFragmentManager.setFragmentResult(NoteFragment.REQUEST_KEY, Bundle())
            }
            textViewImage.setOnClickListener {
                parentFragmentManager.setFragmentResult(NoteFragment.REQUEST_KEY, Bundle())
            }
        }
    }
}