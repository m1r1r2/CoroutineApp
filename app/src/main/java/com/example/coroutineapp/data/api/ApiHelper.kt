package com.example.coroutineapp.data.api

import com.example.coroutineapp.data.model.ApiUser

interface ApiHelper {

     suspend fun getUsers():List<ApiUser>

     suspend fun getMoreUsers():List<ApiUser>

     suspend fun getUsersWithError():List<ApiUser>
}