package com.example.coroutineapp.ui.errorhandling.exceptionhandler

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coroutineapp.data.api.ApiHelper
import com.example.coroutineapp.data.local.DatabaseHelper
import com.example.coroutineapp.data.model.ApiUser
import com.example.coroutineapp.ui.base.UIState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class ExceptionHandlerViewModel(private val apiHelper: ApiHelper,private val databaseHelper: DatabaseHelper):ViewModel() {

    private val uiState = MutableLiveData<UIState<List<ApiUser>>>()

    private val exceptionHandler = CoroutineExceptionHandler { _, e ->
        uiState.postValue(UIState.Error("exception handler: $e"))
    }

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch(exceptionHandler) {
            uiState.postValue(UIState.Loading)
            val usersFromApi = apiHelper.getUsersWithError()
            uiState.postValue(UIState.Success(usersFromApi))
        }
    }

    fun getUiState(): LiveData<UIState<List<ApiUser>>> {
        return uiState
    }

}