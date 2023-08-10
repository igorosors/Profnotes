package com.example.profnotes.di.module

import com.example.profnotes.data.remote.ApiService
import com.example.profnotes.data.remote.RefreshApiService
import com.example.profnotes.data.remote.interceptor.TokenInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiServiceModule {

    companion object {
        const val BASE_URL = "https://ktortogether.profsoft.online/"
        const val HEADER_AUTHORIZATION = "Authorization"
        const val HEADER_BEARER = "Bearer "
    }

    @Qualifier
    annotation class RefreshTokenClient

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        gson: Gson,
        client: OkHttpClient
    ): Retrofit {
        return Retrofit
            .Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    @RefreshTokenClient
    fun provideRefreshTokenRetrofit(
        gson: Gson
    ): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(
        retrofit: Retrofit
    ): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideRefreshApiService(
        @RefreshTokenClient retrofit: Retrofit
    ): RefreshApiService {
        return retrofit.create(RefreshApiService::class.java)
    }


    @Singleton
    @Provides
    fun provideOkHttpClient(
        tokenInterceptor: TokenInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(tokenInterceptor).build()
    }
}