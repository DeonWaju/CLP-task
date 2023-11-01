package com.example.taskcdp.domain.usecases

import com.example.taskcdp.data.local.entity.UserProfile
import kotlinx.coroutines.flow.Flow

interface UserProfileRepository {
    suspend fun invoke(): Flow<UserProfile?>
}