package com.example.coroutineapp.data.api

import com.example.coroutineapp.data.model.ApiUser
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getUsers():List<ApiUser>

    @GET("more-users")
    suspend fun getMoreUsers():List<ApiUser>

    @GET("error")
    suspend fun getUsersWithError():List<ApiUser>
}