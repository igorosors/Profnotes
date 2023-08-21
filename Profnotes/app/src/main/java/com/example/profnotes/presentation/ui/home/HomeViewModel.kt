package com.example.profnotes.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profnotes.data.model.HomeData
import com.example.profnotes.data.model.LoadingState
import com.example.profnotes.data.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
) : ViewModel() {

    private val _homeLiveData = MutableLiveData<LoadingState<HomeData>>()
    val homeLiveData: LiveData<LoadingState<HomeData>> = _homeLiveData

    fun subscribeToData() {
        viewModelScope.launch {
            try {
                _homeLiveData.postValue(LoadingState.Loading())
                homeRepository.getDataFlow().collect() { data ->
                    if (data.courses.isEmpty()) getData()
                    else _homeLiveData.postValue(LoadingState.Success(data))
                }
            } catch (e: Exception) {
                _homeLiveData.postValue(LoadingState.Error(e))
            }
        }
    }

    fun getData(isRefresh: Boolean = false) {
        viewModelScope.launch {
            try {
                if (!isRefresh) {
                    _homeLiveData.postValue(LoadingState.Loading())
                }
                homeRepository.saveData(homeRepository.loadData())
            } catch (e: Exception) {
                _homeLiveData.postValue(LoadingState.Error(e))
            }
        }
    }

}