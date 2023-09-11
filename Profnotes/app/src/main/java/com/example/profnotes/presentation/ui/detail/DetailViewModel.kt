package com.example.profnotes.presentation.ui.detail

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.example.profnotes.data.model.LoadingState
import com.example.profnotes.data.model.content.ContentData
import com.example.profnotes.data.model.content.RichText
import com.example.profnotes.data.model.course.Course
import com.example.profnotes.data.model.note.Note
import com.example.profnotes.data.repository.CoursesRepository
import com.example.profnotes.data.repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val coursesRepository: CoursesRepository,
    private val notesRepository: NotesRepository,
): ViewModel() {

    private val _contentLiveData = MutableLiveData<LoadingState<List<ContentData>>>()
    val contentLiveData: LiveData<LoadingState<List<ContentData>>> = _contentLiveData

    fun getContentData(context: Context, content: List<RichText>) {
        _contentLiveData.postValue(LoadingState.Loading())
        val contentData = mutableListOf<ContentData>()
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                content.forEach {
                    if (it.url.isNullOrEmpty()) {
                        contentData.add(ContentData(
                            text = it.text,
                            url = null,
                            bitmap = null
                        ))
                    } else {
                        val bitmap: Bitmap? = try {
                            Glide.with(context)
                                .asBitmap()
                                .load(it.url)
                                .submit()
                                .get()
                        } catch (_: Exception) { null }

                        if (bitmap == null) {
                            contentData.add(ContentData(
                                text = it.text,
                                url = null,
                                bitmap = null
                            ))
                        } else {
                            contentData.add(ContentData(
                                text = it.text,
                                url = it.url,
                                bitmap = bitmap
                            ))
                        }
                    }
                }
                _contentLiveData.postValue(LoadingState.Success(contentData))
            }
        }
    }

    fun saveCourse(course: Course) {
        viewModelScope.launch {
            try {
                coursesRepository.saveCourses(listOf(course))
            } catch(e: Exception) {

            }
        }
    }

    fun saveNote(note: Note) {
        viewModelScope.launch {
            try {
                notesRepository.saveNote(note)
            } catch(e: Exception) {

            }
        }
    }

}