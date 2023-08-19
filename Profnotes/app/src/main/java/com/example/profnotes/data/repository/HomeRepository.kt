package com.example.profnotes.data.repository

import com.example.profnotes.data.model.HomeData
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    suspend fun loadData(): HomeData

    suspend fun saveData(homeData: HomeData)

    suspend fun getDataFlow(): Flow<HomeData>

}