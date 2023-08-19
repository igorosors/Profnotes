package com.example.profnotes.presentation.ui.note

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.profnotes.data.model.LoadingState
import com.example.profnotes.data.model.Note
import com.example.profnotes.data.model.RichText
import com.example.profnotes.data.repository.NotesRepository
import com.example.profnotes.presentation.ui.note.NoteFragment.Companion.TYPE_LOCAL
import com.example.profnotes.presentation.ui.note.model.Image
import com.example.profnotes.presentation.ui.note.model.NoteData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val notesRepository: NotesRepository
) : ViewModel() {

    private val _noteLiveData = MutableLiveData<List<NoteData>>()
    val noteLiveData: LiveData<List<NoteData>> = _noteLiveData

    private val _imageLiveData = MutableLiveData<LoadingState<Image>>()
    val imageLiveData: LiveData<LoadingState<Image>> = _imageLiveData

    fun addText() {
        val dataList = mutableListOf<NoteData>().apply {
            addAll(_noteLiveData.value.orEmpty())
        }
        dataList.add(NoteData("", null, null))
        _noteLiveData.postValue(dataList)
    }

    fun addImage(image: Image) {
        val dataList = mutableListOf<NoteData>().apply {
            addAll(_noteLiveData.value.orEmpty())
        }
        val position = dataList.size - 1
        // Если список непустой, проверяем, есть ли картинка у последнего элемента
        // иначе вставляем новый элемент
        if ((position != -1) && (dataList[position].url == null)) {
            dataList[position] = dataList[position].copy(url = image.url, bitmap = image.bitmap)
            _noteLiveData.postValue(dataList)
        } else {
            dataList.add(NoteData(null, image.url, image.bitmap))
            _noteLiveData.postValue(dataList)
        }
    }

    fun updateItem(newText: String?, position: Int) {
        Log.d("update", newText.toString() + " at " + position.toString())
        val dataList = mutableListOf<NoteData>().apply {
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
                    super.onLoadFailed(errorDrawable)
                    _imageLiveData.postValue(LoadingState.Error(Exception()))
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }

    fun saveNote(type: Int, title: String) {
        if (type == TYPE_LOCAL) {
            viewModelScope.launch {
                notesRepository.saveNote(
                    Note(
                        id = UUID.randomUUID().toString(),
                        isLocal = true,
                        title = title,
                        content = dataToContent(),
                        date = System.currentTimeMillis().toString()
                    )
                )
            }
        } else {
            viewModelScope.launch {

            }
        }
    }

    private fun dataToContent(): List<RichText> {
        val noteData = noteLiveData.value.orEmpty()

        val res = noteData.map {
            RichText(
                text = it.text,
                image = it.url
            )
        }
        Log.d("SAVE", res.toString())
        return res
    }

}
