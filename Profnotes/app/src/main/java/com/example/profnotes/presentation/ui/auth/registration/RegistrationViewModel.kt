package com.example.profnotes.presentation.ui.auth.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profnotes.data.model.LoadingState
import com.example.profnotes.data.repository.AuthRepository
import com.example.profnotes.data.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenRepository: TokenRepository,
) : ViewModel() {

    private val _registrationLiveData = MutableLiveData<LoadingState<Unit>>()
    val registrationLiveData: LiveData<LoadingState<Unit>> = _registrationLiveData

    private fun toMd5(string: String): String {
        try {
            // Create MD5 Hash
            val digest: MessageDigest = MessageDigest
                .getInstance("MD5")
            digest.update(string.toByteArray())
            val messageDigest: ByteArray = digest.digest()

            // Create Hex String
            val hexString = StringBuilder()
            for (aMessageDigest in messageDigest) {
                var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
                while (h.length < 2) h = "0$h"
                hexString.append(h)
            }
            return hexString.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return ""
    }

    fun register(
        name: String,
        surname: String,
        phone: String,
        password: String
    ) {
        viewModelScope.launch {
            try {
                _registrationLiveData.postValue(LoadingState.Loading())
                val token = authRepository.registration(
                    name = name,
                    surname = surname,
                    avatar = "",
                    phone = phone,
                    password = toMd5(password)
                )
                tokenRepository.token = token.token
                tokenRepository.login = phone
                tokenRepository.password = password
                _registrationLiveData.postValue(LoadingState.Success(Unit))
            } catch (e: Exception) {
                _registrationLiveData.postValue(LoadingState.Error(e))
            }
        }

    }

}