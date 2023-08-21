package com.example.profnotes.presentation.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ViewFlipper
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.profnotes.R
import com.example.profnotes.data.model.ApiError
import com.example.profnotes.data.model.LoadingState
import com.example.profnotes.databinding.ViewEmptyStateBinding
import com.example.profnotes.databinding.ViewErrorStateBinding
import com.example.profnotes.databinding.ViewLoadingStateBinding
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class StateViewFlipper(context: Context, attrs: AttributeSet? = null) : ViewFlipper(context, attrs) {

    enum class State(val displayedChild: Int) {
        LOADING(0),
        ERROR(1),
        EMPTY(2),
        DATA(3)
    }

    private val loadingBinding = ViewLoadingStateBinding.inflate(LayoutInflater.from(context), this, true)
    private val errorBinding = ViewErrorStateBinding.inflate(LayoutInflater.from(context), this, true)
    private val emptyBinding = ViewEmptyStateBinding.inflate(LayoutInflater.from(context), this, true)

    fun <T> setState(result: LoadingState<T>) {
        when (result) {
            is LoadingState.Loading -> setStateLoading()
            is LoadingState.Success -> setStateData()
            is LoadingState.Error -> setStateError(result.exception)
        }
    }

    fun setEmptyState() {
        displayedChild = State.EMPTY.displayedChild
    }

    fun setRetryMethod(retry: () -> Unit) {
        errorBinding.buttonRepeat.setOnClickListener { retry.invoke() }
    }

    private fun setStateLoading() {
        displayedChild = State.LOADING.displayedChild
    }

    private fun setStateData() {
        displayedChild = State.DATA.displayedChild
    }

    private fun setStateError(exception: Exception) {
        displayedChild = State.ERROR.displayedChild
        when {
            exception.isNetworkError -> setNetworkError()
            exception is ApiError -> setApiError(exception.message)
            else -> setGeneralError(exception)

        }
    }

    private fun setNetworkError() {
        setErrorStateContent(
            title = context.getString(R.string.error_no_network_title),
            description = context.getString(R.string.error_no_network_description),
        )
    }

    private fun setGeneralError(e: Exception) {
        setErrorStateContent(
            title = context.getString(R.string.error_something_wrong_title),
            description = e.message.toString(),
        )
    }

    private fun setApiError(description: String?) {
        setErrorStateContent(
            title = context.getString(R.string.error_something_wrong_title),
            description = description ?: "",
        )
    }

    private fun setErrorStateContent(title: String, description: String) = with(errorBinding) {
        textViewErrorTitle.text = title
        textViewErrorMessage.text = description
    }

    private val Exception.isNetworkError: Boolean
        get() = when (this) {
            is ConnectException,
            is UnknownHostException,
            is SocketTimeoutException -> true
            else -> false
        }
}
