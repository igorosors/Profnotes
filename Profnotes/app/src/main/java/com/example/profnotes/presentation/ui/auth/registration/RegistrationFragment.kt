package com.example.profnotes.presentation.ui.auth.registration

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.profnotes.R
import com.example.profnotes.data.model.ApiError
import com.example.profnotes.databinding.FragmentRegistrationBinding
import com.example.profnotes.presentation.extensions.applyKeyboardInsets
import com.example.profnotes.presentation.ui.base.BaseFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


@AndroidEntryPoint
class RegistrationFragment : BaseFragment(R.layout.fragment_registration) {

    private val binding by viewBinding(FragmentRegistrationBinding::bind)
    private val viewModel: RegistrationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            root.applyKeyboardInsets()
            buttonLogin.setOnClickListener {
                findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
            }
            buttonRegistration.setOnClickListener {
                viewModel.register(
                    name = editTextName.text.toString(),
                    surname = editTextSurname.text.toString(),
                    phone = editTextPhone.text.toString(),
                    password = editTextPassword.text.toString()
                )
            }
            viewModel.registrationLiveData.observe(viewLifecycleOwner) { state ->
                state.doOnLoading {
                    progressBar.visibility = View.VISIBLE
                    buttonRegistration.text = ""
                }
                state.doOnSuccess {
                    progressBar.visibility = View.GONE
                    buttonRegistration.text = getString(R.string.registration_button_label)
                    findNavController().navigate(R.id.action_registrationFragment_to_homeFragment)
                }
                state.doOnError {
                    progressBar.visibility = View.GONE
                    buttonRegistration.text = getString(R.string.registration_button_label)
                    showSnackbar(it)
                }
            }
        }
    }

    private fun showSnackbar(e: Exception) {
        when (e) {
            is ConnectException,
            is UnknownHostException,
            is SocketTimeoutException -> Snackbar.make(
                requireView(), requireContext().getString(R.string.error_no_network_title), Snackbar.LENGTH_SHORT
            ).show()
            is ApiError -> Snackbar.make(
                requireView(), e.message, Snackbar.LENGTH_SHORT
            ).show()
            else -> Snackbar.make(
                requireView(), requireContext().getString(R.string.error_something_wrong_title), Snackbar.LENGTH_SHORT
            ).show()
        }
    }


}