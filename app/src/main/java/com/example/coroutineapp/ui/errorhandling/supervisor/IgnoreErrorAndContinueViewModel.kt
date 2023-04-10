package com.example.coroutineapp.ui.errorhandling.supervisor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coroutineapp.data.api.ApiHelper
import com.example.coroutineapp.data.local.DatabaseHelper
import com.example.coroutineapp.data.model.ApiUser
import com.example.coroutineapp.ui.base.UIState
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class IgnoreErrorAndContinueViewModel(private val apiHelper: ApiHelper,private val databaseHelper: DatabaseHelper):ViewModel() {

    private val uiState = MutableLiveData<UIState<List<ApiUser>>>()

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            uiState.postValue(UIState.Loading)
            // supervisorScope is needed, so that we can ignore error and continue
            // here, more than two child jobs are running in parallel under a supervisor, one child job gets failed, we can continue with other.
            supervisorScope {
                val usersFromApiDeferred = async { apiHelper.getUsersWithError() }
                val moreUsersFromApiDeferred = async { apiHelper.getMoreUsers() }

                val usersFromApi = try {
                    usersFromApiDeferred.await()
                } catch (e: Exception) {
                    emptyList()
                }

                val moreUsersFromApi = try {
                    moreUsersFromApiDeferred.await()
                } catch (e: Exception) {
                    emptyList()
                }

                val allUsersFromApi = mutableListOf<ApiUser>()
                allUsersFromApi.addAll(usersFromApi)
                allUsersFromApi.addAll(moreUsersFromApi)

                uiState.postValue(UIState.Success(allUsersFromApi))
            }
        }
    }

    fun getUiState(): LiveData<UIState<List<ApiUser>>> {
        return uiState
    }

}