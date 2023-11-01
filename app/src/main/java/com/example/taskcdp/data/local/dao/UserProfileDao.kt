package com.example.taskcdp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.taskcdp.data.local.entity.UserProfile
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(userProfile: UserProfile)

    @Query("SELECT * FROM UserProfile LIMIT 1")
    fun getUserProfile(): Flow<UserProfile?>

    @Query("DELETE FROM UserProfile")
    suspend fun deleteAll()
}