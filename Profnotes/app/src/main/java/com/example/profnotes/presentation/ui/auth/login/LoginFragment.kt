package com.example.profnotes.presentation.ui.auth.login

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.profnotes.R
import com.example.profnotes.data.model.ApiError
import com.example.profnotes.databinding.FragmentLoginBinding
import com.example.profnotes.presentation.extensions.applyKeyboardInsets
import com.example.profnotes.presentation.ui.base.BaseFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

@AndroidEntryPoint
class LoginFragment : BaseFragment(R.layout.fragment_login) {

    private val binding by viewBinding(FragmentLoginBinding::bind)
    private val viewModel: LoginViewModel by viewModels()

    override fun callOperations() {
        if (viewModel.isAutoLogin()) {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            root.applyKeyboardInsets()
            buttonRegistration.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
            }
            buttonLogin.setOnClickListener {
                viewModel.login(editTextPhone.text.toString(), editTextPassword.text.toString())
            }
            viewModel.loginLiveData.observe(viewLifecycleOwner) { state ->
                state.doOnLoading {
                    progressBar.visibility = View.VISIBLE
                    buttonLogin.text = ""
                }
                state.doOnSuccess {
                    progressBar.visibility = View.GONE
                    buttonLogin.text = getString(R.string.login_button_label)
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                }
                state.doOnError {
                    progressBar.visibility = View.GONE
                    buttonLogin.text = getString(R.string.login_button_label)
                    showError(it)
                }
            }
        }
    }

    private fun showError(e: Exception) {
        when (e) {
            is ConnectException,
            is UnknownHostException,
            is SocketTimeoutException -> showSnackbar(requireContext().getString(R.string.error_no_network_title))
            is ApiError -> showSnackbar(e.message)
            else -> showSnackbar(requireContext().getString(R.string.error_something_wrong_description))
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(
            requireView(), message, Snackbar.LENGTH_SHORT
        ).apply {
            setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.red))
        }.show()
    }

}