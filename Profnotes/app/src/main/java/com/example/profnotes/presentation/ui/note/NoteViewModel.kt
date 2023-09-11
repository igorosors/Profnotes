package com.example.profnotes.presentation.ui.note

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.profnotes.data.model.LoadingState
import com.example.profnotes.data.model.note.Note
import com.example.profnotes.data.model.content.RichText
import com.example.profnotes.data.repository.NotesRepository
import com.example.profnotes.presentation.ui.note.NoteFragment.Companion.TYPE_LOCAL
import com.example.profnotes.data.model.content.Image
import com.example.profnotes.data.model.content.ContentData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val notesRepository: NotesRepository
) : ViewModel() {

    private val _noteLiveData = MutableLiveData<List<ContentData>>()
    val noteLiveData: LiveData<List<ContentData>> = _noteLiveData

    private val _imageLiveData = MutableLiveData<LoadingState<Image>>()
    val imageLiveData: LiveData<LoadingState<Image>> = _imageLiveData

    private val _saveLiveData = MutableLiveData<LoadingState<Unit>>()
    val saveLiveData: LiveData<LoadingState<Unit>> = _saveLiveData

    fun addText() {
        val dataList = mutableListOf<ContentData>().apply {
            addAll(_noteLiveData.value.orEmpty())
        }
        dataList.add(ContentData("", null, null))
        _noteLiveData.postValue(dataList)
    }

    fun addImage(image: Image) {
        val dataList = mutableListOf<ContentData>().apply {
            addAll(_noteLiveData.value.orEmpty())
        }
        val position = dataList.size - 1
        // Если список непустой, проверяем, есть ли картинка у последнего элемента
        // иначе вставляем новый элемент
        if ((position != -1) && (dataList[position].url == null)) {
            dataList[position] = dataList[position].copy(url = image.url, bitmap = image.bitmap)
            _noteLiveData.postValue(dataList)
        } else {
            dataList.add(ContentData(null, image.url, image.bitmap))
            _noteLiveData.postValue(dataList)
        }
    }

    fun updateItem(newText: String?, position: Int) {
        val dataList = mutableListOf<ContentData>().apply {
            addAll(_noteLiveData.value.orEmpty())
        }
        if (dataList[position].text != newText) {
            dataList[position] = dataList[position].copy(text = newText)
            _noteLiveData.postValue(dataList)
        }
    }

    fun getImage(context: Context, url: String) {
        _imageLiveData.postValue(LoadingState.Loading())
        Glide.with(context)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    _imageLiveData.postValue(LoadingState.Success(Image(url, resource)))
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    _imageLiveData.postValue(LoadingState.Error(Exception()))
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }

    fun saveNote(type: Int, title: String) {
        if (!(title.isEmpty() && noteLiveData.value.isNullOrEmpty())) {
            if (type == TYPE_LOCAL) {
                viewModelScope.launch {
                    _saveLiveData.postValue(LoadingState.Loading())
                    try {
                        notesRepository.saveNote(
                            Note(
                                id = UUID.randomUUID().toString(),
                                isLocal = true,
                                title = title,
                                content = fromDataToContent(),
                                date = System.currentTimeMillis()
                            )
                        )
                        _saveLiveData.postValue(LoadingState.Success(Unit))
                    } catch (e: Exception) {
                        _saveLiveData.postValue(LoadingState.Error(e))
                    }
                }
            } else {
                viewModelScope.launch {
                    _saveLiveData.postValue(LoadingState.Loading())
                    try {
                        val note = notesRepository.postNote(
                            title = title,
                            content = fromDataToContent()
                        )
                        notesRepository.saveNote(note)
                        _saveLiveData.postValue(LoadingState.Success(Unit))
                    } catch (e: Exception) {
                        _saveLiveData.postValue(LoadingState.Error(e))
                    }
                }
            }
        }
    }

    private fun fromDataToContent(): List<RichText> {
        val noteData = noteLiveData.value.orEmpty()
        return noteData.map {
            RichText(
                text = it.text,
                url = it.url
            )
        }
    }

}
