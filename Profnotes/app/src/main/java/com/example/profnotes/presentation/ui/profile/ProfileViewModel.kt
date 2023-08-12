package com.example.profnotes.presentation.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profnotes.data.repository.AuthRepository
import com.example.profnotes.data.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val tokenRepository: TokenRepository,
) : ViewModel() {

    fun getNumber(): String = tokenRepository.login.orEmpty()

    fun logout() {
        tokenRepository.token = null
        tokenRepository.login = null
        tokenRepository.password = null
    }
}