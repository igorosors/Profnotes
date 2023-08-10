package com.example.profnotes.presentation.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profnotes.data.model.ApiError
import com.example.profnotes.data.model.Course
import com.example.profnotes.data.model.LoadingState
import com.example.profnotes.data.remote.ApiService
import com.example.profnotes.data.repository.CoursesRepository
import com.example.profnotes.data.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val coursesRepository: CoursesRepository,
) : ViewModel() {

    private val _homeLiveData = MutableLiveData<LoadingState<List<Course>>>()
    val homeLiveData: LiveData<LoadingState<List<Course>>> = _homeLiveData


    fun getCourses() {
        viewModelScope.launch {

            try {
                _homeLiveData.postValue(LoadingState.Loading())
                val courses = coursesRepository.getCourses()


                _homeLiveData.postValue(LoadingState.Success(courses))
            } catch (e: Exception) {

                _homeLiveData.postValue(LoadingState.Error(e))

            }
        }
    }


}