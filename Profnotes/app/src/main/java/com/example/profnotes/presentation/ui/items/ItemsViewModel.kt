package com.example.profnotes.presentation.ui.items

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profnotes.data.model.Data
import com.example.profnotes.data.model.LoadingState
import com.example.profnotes.data.repository.CoursesRepository
import com.example.profnotes.data.repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor(
    private val coursesRepository: CoursesRepository,
    private val notesRepository: NotesRepository,
) : ViewModel() {

    private val _dataLiveData = MutableLiveData<LoadingState<List<Data>>>()
    val dataLiveData: LiveData<LoadingState<List<Data>>> = _dataLiveData

    fun subscribeToCourses() {
        viewModelScope.launch {
            try {
                _dataLiveData.postValue(LoadingState.Loading())
                coursesRepository.getCoursesFlow().collect() {
                    _dataLiveData.postValue(LoadingState.Success(it))
                }
            } catch (e: Exception) {
                _dataLiveData.postValue(LoadingState.Error(e))
            }
        }
    }

    fun subscribeToLocalNotes() {
        viewModelScope.launch {
            try {
                _dataLiveData.postValue(LoadingState.Loading())
                notesRepository.getLocalNotesFlow().collect() {
                    _dataLiveData.postValue(LoadingState.Success(it))
                }
            } catch (e: Exception) {
                _dataLiveData.postValue(LoadingState.Error(e))
            }
        }
    }

    fun subscribeToCommunityNotes() {
        viewModelScope.launch {
            try {
                _dataLiveData.postValue(LoadingState.Loading())
                notesRepository.getCommunityNotesFlow().collect() {
                    _dataLiveData.postValue(LoadingState.Success(it))
                }
            } catch (e: Exception) {
                _dataLiveData.postValue(LoadingState.Error(e))
            }
        }
    }
}