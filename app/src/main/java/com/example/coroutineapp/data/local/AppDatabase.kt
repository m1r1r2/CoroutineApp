package com.example.coroutineapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.coroutineapp.data.local.dao.UserDao
import com.example.coroutineapp.data.local.entity.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase :RoomDatabase(){

    abstract fun userDao():UserDao
}