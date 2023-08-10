package com.example.profnotes.data.remote.interceptor

import com.example.profnotes.data.repository.RefreshTokenRepository
import com.example.profnotes.data.repository.TokenRepository
import com.example.profnotes.di.module.ApiServiceModule.Companion.HEADER_AUTHORIZATION
import com.example.profnotes.di.module.ApiServiceModule.Companion.HEADER_BEARER
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import java.net.HttpURLConnection
import javax.inject.Inject
import javax.inject.Singleton

//@Singleton
class TokenInterceptor @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .header(HEADER_AUTHORIZATION,  HEADER_BEARER + tokenRepository.token)
            .build()
        val response = chain.proceed(request)
        if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
            response.close()
            val newToken =
                runBlocking {
                    refreshTokenRepository.refreshToken(tokenRepository.login!!, tokenRepository.password!!).also {
                        tokenRepository.token = it.token
                    }
                }
            val newRequest = request
                .newBuilder()
                .header(HEADER_AUTHORIZATION, HEADER_BEARER + newToken.token)
                .build()
            return chain.proceed(newRequest)
        }
        return response
    }
}