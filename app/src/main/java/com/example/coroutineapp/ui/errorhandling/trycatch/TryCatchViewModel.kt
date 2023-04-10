package com.example.coroutineapp.ui.errorhandling.trycatch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coroutineapp.data.api.ApiHelper
import com.example.coroutineapp.data.local.DatabaseHelper
import com.example.coroutineapp.data.model.ApiUser
import com.example.coroutineapp.ui.base.UIState
import kotlinx.coroutines.launch

class TryCatchViewModel(private val apiHelper: ApiHelper, private val databaseHelper: DatabaseHelper):ViewModel() {


    private val uiState = MutableLiveData<UIState<List<ApiUser>>>()

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            uiState.postValue(UIState.Loading)
            try {
                val usersFromApi = apiHelper.getUsersWithError()
                uiState.postValue(UIState.Success(usersFromApi))
            } catch (e: Exception) {
                uiState.postValue(UIState.Error("Something Went Wrong"))
            }
        }
    }

    fun getUiState(): LiveData<UIState<List<ApiUser>>> {
        return uiState
    }

}