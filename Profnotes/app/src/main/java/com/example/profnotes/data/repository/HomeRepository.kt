package com.example.profnotes.data.repository

import com.example.profnotes.data.model.home.HomeData
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    suspend fun loadData(): HomeData

    suspend fun saveData(homeData: HomeData)

    suspend fun getDataFlow(): Flow<HomeData>

    suspend fun search(text: String): Flow<HomeData>
}