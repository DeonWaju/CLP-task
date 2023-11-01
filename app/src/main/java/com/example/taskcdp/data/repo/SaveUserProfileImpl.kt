package com.example.taskcdp.data.repo

import com.example.taskcdp.data.local.dao.UserProfileDao
import com.example.taskcdp.data.local.entity.UserProfile
import com.example.taskcdp.domain.usecases.SaveUserProfileRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveUserProfileImpl @Inject constructor(
    private val userProfileDao: UserProfileDao,
    private val ioDispatcher: CoroutineDispatcher
): SaveUserProfileRepository{
    override suspend fun invoke(userProfile: UserProfile) = withContext(ioDispatcher){
        userProfileDao.upsert(userProfile)
    }
}