package com.example.profnotes.presentation.ui.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profnotes.data.model.LoadingState
import com.example.profnotes.data.model.home.HomeData
import com.example.profnotes.data.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
) : ViewModel() {
    private val _favouriteLiveData = MutableLiveData<LoadingState<HomeData>>()
    val favouriteLiveData: LiveData<LoadingState<HomeData>> = _favouriteLiveData

    private val searchFlow = MutableStateFlow("")

    @OptIn(FlowPreview::class)
    fun search(text: String) {
        searchFlow.value = text
        viewModelScope.launch {
            searchFlow
                .debounce(200)
                .distinctUntilChanged()
                .collect { text ->
                    if (text == "") {
                        try {
                            favoriteRepository.getDataFlow().collect { data ->
                                _favouriteLiveData.postValue(LoadingState.Success(data))
                            }
                        } catch (e: Exception) {
                            _favouriteLiveData.postValue(LoadingState.Error(e))
                        }
                    } else {
                        try {
                            favoriteRepository.search(text).collect { data ->
                                _favouriteLiveData.postValue(LoadingState.Success(data))
                            }
                        } catch (e: Exception) {
                            _favouriteLiveData.postValue(LoadingState.Error(e))
                        }
                    }
                }
        }
    }

    fun subscribeToData() {
        viewModelScope.launch {
            try {
                _favouriteLiveData.postValue(LoadingState.Loading())
                favoriteRepository.getDataFlow().collect { data ->
                    _favouriteLiveData.postValue(LoadingState.Success(data))
                }
            } catch (e: Exception) {
                _favouriteLiveData.postValue(LoadingState.Error(e))
            }
        }
    }

}