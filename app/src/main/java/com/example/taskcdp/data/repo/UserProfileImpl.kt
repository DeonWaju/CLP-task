package com.example.taskcdp.data.repo

import com.example.taskcdp.data.local.dao.UserProfileDao
import com.example.taskcdp.data.local.entity.UserProfile
import com.example.taskcdp.domain.usecases.UserProfileRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserProfileImpl @Inject constructor(
    private val userProfileDao: UserProfileDao,
    private val ioDispatcher: CoroutineDispatcher
): UserProfileRepository {
    override suspend fun invoke(): Flow<UserProfile?> = withContext(ioDispatcher) {
        userProfileDao.getUserProfile()
    }
}