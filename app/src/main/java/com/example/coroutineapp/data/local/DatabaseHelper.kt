package com.example.coroutineapp.data.local

import com.example.coroutineapp.data.local.entity.User


interface DatabaseHelper {
    suspend fun getUsers():List<User>

    suspend fun insertAll(user:List<User>)
}