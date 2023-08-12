package com.example.profnotes.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profnotes.data.model.Course
import com.example.profnotes.data.model.LoadingState
import com.example.profnotes.data.repository.CoursesRepository
import com.example.profnotes.data.repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val coursesRepository: CoursesRepository,
    private val notesRepository: NotesRepository,
) : ViewModel() {

    private val _homeLiveData = MutableLiveData<LoadingState<List<Course>>>()
    val homeLiveData: LiveData<LoadingState<List<Course>>> = _homeLiveData

    fun subscribeToCourses() {
        viewModelScope.launch {
            try {
                _homeLiveData.postValue(LoadingState.Loading())
                coursesRepository.getCoursesFlow().collect { courses ->
                    if (courses.isEmpty()) getCourses()
                    else _homeLiveData.postValue(LoadingState.Success(courses))
                }
            } catch (e: Exception) {
                _homeLiveData.postValue(LoadingState.Error(e))
            }
        }
    }

    fun getCourses(isRefresh: Boolean = false) {
        viewModelScope.launch {
            try {
                if (!isRefresh) {
                    _homeLiveData.postValue(LoadingState.Loading())
                }
                val courses = coursesRepository.getCourses()
                coursesRepository.saveCourses(courses)
                _homeLiveData.postValue(LoadingState.Success(courses))
            } catch (e: Exception) {
                _homeLiveData.postValue(LoadingState.Error(e))
            }
        }
    }

    fun getNotes(isRefresh: Boolean = false) {
        viewModelScope.launch {
            try {
                if (!isRefresh) {
                    _homeLiveData.postValue(LoadingState.Loading())
                }
                val notes = notesRepository.getNotes()
            } catch (e: Exception) {
                _homeLiveData.postValue(LoadingState.Error(e))
            }
        }
    }
}