package com.example.taskcdp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.taskcdp.data.local.dao.UserProfileDao
import com.example.taskcdp.data.local.entity.UserProfile


@Database(entities = [UserProfile::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userProfileDao(): UserProfileDao
}