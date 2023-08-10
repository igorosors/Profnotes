package com.example.profnotes.data.model

sealed class LoadingState<T> {
    class Loading<T> : LoadingState<T>()
    class Success<T>(val data: T) : LoadingState<T>()
    class Error<T>(val exception: Exception) : LoadingState<T>()

    fun doOnSuccess(block: (T) -> Unit) {
        if (this is Success) {
            block(this.data)
        }
    }

    fun doOnError(block: (Exception) -> Unit) {
        if (this is Error) {
            block(this.exception)
        }
    }

    fun doOnLoading(block: () -> Unit) {
        if (this is Loading) {
            block()
        }
    }
}
