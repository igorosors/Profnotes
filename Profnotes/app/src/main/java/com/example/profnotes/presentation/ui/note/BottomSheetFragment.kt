package com.example.profnotes.presentation.ui.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.profnotes.R
import com.example.profnotes.databinding.FragmentBottomSheetBinding
import com.example.profnotes.presentation.extensions.applyBottomInsets
import com.example.profnotes.presentation.ui.note.NoteFragment.Companion.EXTRA_URL
import com.example.profnotes.presentation.ui.note.NoteFragment.Companion.ITEM_IMAGE
import com.example.profnotes.presentation.ui.note.NoteFragment.Companion.ITEM_KEY
import com.example.profnotes.presentation.ui.note.NoteFragment.Companion.ITEM_TEXT
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment : BottomSheetDialogFragment() {

    private val binding by viewBinding(FragmentBottomSheetBinding::bind)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_bottom_sheet, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Без этой строчки фрагмент начинается не от края, образуя пустоту в нав баре
        view.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        view.applyBottomInsets()

        with(binding) {
            textViewText.setOnClickListener {
                parentFragmentManager.setFragmentResult(NoteFragment.REQUEST_KEY, Bundle().apply {
                    putInt(ITEM_KEY, ITEM_TEXT)
                })
            }
            textViewImage.setOnClickListener {
                expand()
            }
            buttonUpload.setOnClickListener {
                parentFragmentManager.setFragmentResult(NoteFragment.REQUEST_KEY, Bundle().apply {
                    putInt(ITEM_KEY, ITEM_IMAGE)
                    putString(EXTRA_URL, editTextUrl.text.toString())
                })
            }
        }
    }

    private fun expand() {
        val bottomSheet = requireDialog().findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
        val transition = ChangeBounds()
        transition.duration = 200
        TransitionManager.beginDelayedTransition(bottomSheet, transition)
        binding.layoutExpanded.visibility = View.VISIBLE
    }


}