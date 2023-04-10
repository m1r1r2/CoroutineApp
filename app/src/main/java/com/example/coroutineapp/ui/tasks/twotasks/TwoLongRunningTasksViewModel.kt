package com.example.coroutineapp.ui.tasks.twotasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coroutineapp.data.api.ApiHelper
import com.example.coroutineapp.data.local.DatabaseHelper

import com.example.coroutineapp.ui.base.UIState
import kotlinx.coroutines.*

class TwoLongRunningTasksViewModel(private val apiHelper: ApiHelper, private val databaseHelper: DatabaseHelper):ViewModel() {

    private val uiState=MutableLiveData<UIState<String>>()

    fun startLongRunningTask(){
        viewModelScope.launch {
            uiState.postValue(UIState.Loading)
            try {
                // do long running tasks
                val resultOneDeferred = async { doLongRunningTaskOne() }
                val resultTwoDeferred = async { doLongRunningTaskTwo() }
                val combinedResult = resultOneDeferred.await() + resultTwoDeferred.await()

                uiState.postValue(UIState.Success("Task Completed : $combinedResult"))
            } catch (e: Exception) {
                uiState.postValue(UIState.Error("Something Went Wrong"))
            }
        }
    }

    fun getUiState():LiveData<UIState<String>>{
        return uiState
    }

    private suspend fun doLongRunningTaskOne(): Int {
        return withContext(Dispatchers.Default) {
            // your code for doing a long running task
            // Added delay to simulate
            delay(2000)
            return@withContext 10
        }
    }

    private suspend fun doLongRunningTaskTwo(): Int {
        return withContext(Dispatchers.Default) {
            // your code for doing a long running task
            // Added delay to simulate
            delay(2000)
            return@withContext 10
        }
    }
}