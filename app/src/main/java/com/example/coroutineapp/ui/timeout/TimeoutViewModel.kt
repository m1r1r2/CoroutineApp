package com.example.coroutineapp.ui.timeout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coroutineapp.data.api.ApiHelper
import com.example.coroutineapp.data.local.DatabaseHelper
import com.example.coroutineapp.data.model.ApiUser
import com.example.coroutineapp.ui.base.UIState
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

class TimeoutViewModel(private val apiHelper: ApiHelper, private val databaseHelper: DatabaseHelper):ViewModel() {

    private val UiState=MutableLiveData<UIState<List<ApiUser>>>()
    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            UiState.postValue(UIState.Loading)
            try{
                withTimeout(100){
                    val userfromApi=apiHelper.getUsers()
                    UiState.postValue(UIState.Success(userfromApi))
                }
            }
            catch (e: TimeoutCancellationException) {
                UiState.postValue(UIState.Error("TimeoutCancellationException"))
            } catch (e: Exception) {
                UiState.postValue(UIState.Error("Something Went Wrong"))
            }
        }

    }

    fun getUiState(): LiveData<UIState<List<ApiUser>>> {
        return UiState
    }
}