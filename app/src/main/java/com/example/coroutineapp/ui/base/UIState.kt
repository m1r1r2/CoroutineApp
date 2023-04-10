package com.example.coroutineapp.ui.base

sealed interface UIState<out T>{

    data class Success<T>(val data: T) : UIState<T>

    data class Error(val message: String) : UIState<Nothing>

    object Loading : UIState<Nothing>
}