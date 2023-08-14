package com.example.profnotes.presentation.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.profnotes.R
import com.example.profnotes.databinding.FragmentProfileBinding
import com.example.profnotes.presentation.extensions.applyTopInsets
import com.example.profnotes.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            appBarLayout.applyTopInsets()

            textViewTitle.text = StringBuilder()
                .append(getString(R.string.profile_text_title))
                .append(viewModel.getNumber())
            buttonLogout.setOnClickListener {
                viewModel.logout()
                findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
            }
        }

    }



}