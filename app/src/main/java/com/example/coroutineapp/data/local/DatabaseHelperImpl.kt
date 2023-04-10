package com.example.coroutineapp.data.local

import com.example.coroutineapp.data.local.entity.User

class DatabaseHelperImpl(private val appDatabase: AppDatabase):DatabaseHelper{
    override suspend fun getUsers() =appDatabase.userDao().getAll()


    override suspend fun insertAll(user: List<User>) =appDatabase.userDao().insertAll(user)
}