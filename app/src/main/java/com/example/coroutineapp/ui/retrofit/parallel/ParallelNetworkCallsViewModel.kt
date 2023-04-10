package com.example.coroutineapp.ui.retrofit.parallel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coroutineapp.data.api.ApiHelper
import com.example.coroutineapp.data.local.DatabaseHelper
import com.example.coroutineapp.data.model.ApiUser
import com.example.coroutineapp.ui.base.UIState
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class ParallelNetworkCallsViewModel(private val apiHelper: ApiHelper,private val databaseHelper: DatabaseHelper):ViewModel() {

    private val uiState = MutableLiveData<UIState<List<ApiUser>>>()

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            uiState.postValue(UIState.Loading)
            try {
                // coroutineScope is needed, else in case of any network error, it will crash
                coroutineScope {
                    val usersFromApiDeferred = async { apiHelper.getUsers() }
                    val moreUsersFromApiDeferred = async { apiHelper.getMoreUsers() }

                    val usersFromApi = usersFromApiDeferred.await()
                    val moreUsersFromApi = moreUsersFromApiDeferred.await()

                    val allUsersFromApi = mutableListOf<ApiUser>()
                    allUsersFromApi.addAll(usersFromApi)
                    allUsersFromApi.addAll(moreUsersFromApi)

                    uiState.postValue(UIState.Success(allUsersFromApi))
                }
            } catch (e: Exception) {
                uiState.postValue(UIState.Error("Something Went Wrong"))
            }
        }
    }

    fun getUiState(): LiveData<UIState<List<ApiUser>>> {
        return uiState
    }
}