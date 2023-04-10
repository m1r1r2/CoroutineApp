package com.example.coroutineapp.ui.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coroutineapp.data.api.ApiHelper
import com.example.coroutineapp.data.local.DatabaseHelper
import com.example.coroutineapp.data.local.entity.User
import com.example.coroutineapp.ui.base.UIState
import kotlinx.coroutines.launch

class RoomDBViewModel(private val apiHelper: ApiHelper, private val dbHelper: DatabaseHelper) :
    ViewModel() {

    private val uiState = MutableLiveData<UIState<List<User>>>()

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            uiState.postValue(UIState.Loading)
            try {
                val usersFromDb = dbHelper.getUsers()
                if (usersFromDb.isEmpty()) {
                    val usersFromApi = apiHelper.getUsers()
                    val usersToInsertInDB = mutableListOf<User>()

                    for (apiUser in usersFromApi) {
                        val user = User(
                            apiUser.id,
                            apiUser.name,
                            apiUser.email,
                            apiUser.avatar
                        )
                        usersToInsertInDB.add(user)
                    }

                    dbHelper.insertAll(usersToInsertInDB)

                    uiState.postValue(UIState.Success(usersToInsertInDB))

                } else {
                    uiState.postValue(UIState.Success(usersFromDb))
                }


            } catch (e: Exception) {
                uiState.postValue(UIState.Error("Something Went Wrong"))
            }
        }
    }

    fun getUiState(): LiveData<UIState<List<User>>> {
        return uiState
    }
}